package com.azezara.sokobangame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import java.util.ArrayList;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azezara.sokobangame.model.SokobanController;
import com.azezara.sokobangame.model.Direction;


    public class MainActivity extends Activity {
        private Chronometer chronometer;
        private long pause;
        private boolean isRunning;

        final SokobanController sokoban = new SokobanController();
        TextView NewText;
        TextView TitleText;
        int count = 0;
        MediaPlayer mp;
        private Accelerometer accelerometer;
        private Gyroscope gyroscope;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // DEFAULT APP SETUP
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        startChronometer(chronometer);

        Intent intent = getIntent();
        final MediaPlayer successMP = MediaPlayer.create(this, R.raw.success);
        final MediaPlayer footstepMP = MediaPlayer.create(this, R.raw.footstep);
        mp = successMP;
        count = 0;

        final String currentLevelName = intent.getStringExtra(LevelMenuActivity.EXTRA_TEXT);
        final String currentLevelMap = intent.getStringExtra(LevelMenuActivity.EXTRA_TEXT2);
        sokoban.runLevel(currentLevelName, currentLevelMap);

        final ArrayList view = sokoban.getView();
        final ImageAdapter adapter = new ImageAdapter(this, view);

        gyroscope = new Gyroscope(this);
        accelerometer = new Accelerometer(this);

        String scoreScr = String.valueOf(sokoban.getLevelInfo());
        NewText = (TextView)findViewById(R.id.textview1);
        NewText.setText(scoreScr);

        String titleScr = "Level Name: " + String.valueOf(sokoban.game.getCurrentLevelName());
        TitleText = (TextView)findViewById(R.id.textview2);
        TitleText.setText(titleScr);

        final Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count = 0;
                sokoban.runLevel(currentLevelName, currentLevelMap);
                adapter.updateGrid(sokoban.getView());
                NewText.setText(sokoban.getLevelInfo());
                String titleScr = "Level Name: " + String.valueOf(sokoban.game.getCurrentLevelName());
                TitleText.setText(titleScr);
                TitleText.setTextColor(Color.parseColor("#000000"));
                TitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                resetChronometer(chronometer);
                startChronometer(chronometer);
            }
        });

        final Button buttonMenu = findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMenu();
            }
        });

        final GridView gridview = (GridView)findViewById(R.id.gridview1);

        gridview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    int[] workerPoint = sokoban.getPlayerLocation();
                    int xCoord = (int) event.getX();
                    int yCoord = (int) event.getY();

                    int position = gridview.pointToPosition(xCoord, yCoord);

                    ImageView touchedView = (ImageView) gridview.getChildAt(position);
                    int touchedIndex = touchedView.getId();
                    int[] touchedCoords = getTouchedCoords(touchedIndex);

                    boolean movedLeft = touchedCoords[0] == workerPoint[0] && touchedCoords[1] == workerPoint[1]-1;
                    boolean movedRight = touchedCoords[0] == workerPoint[0] && touchedCoords[1]-1 == workerPoint[1];
                    boolean movedDown = touchedCoords[0] == workerPoint[0]+1 && touchedCoords[1] == workerPoint[1];
                    boolean movedUp = touchedCoords[0] == workerPoint[0]-1 && touchedCoords[1] == workerPoint[1];

                    if (movedLeft) {
                        update(adapter, Direction.LEFT, footstepMP);
                    }

                    if (movedRight) {
                        update(adapter, Direction.RIGHT, footstepMP);
                    }

                    if (movedDown) {
                        update(adapter, Direction.DOWN, footstepMP);
                    }

                    if (movedUp) {
                        update(adapter, Direction.UP, footstepMP);
                    }

                }
                return true;
            }
        });

        // gyroscope.setListener(new Gyroscope.Listener() {
        //     @Override
        //     public void onRotation(float rx, float ry, float rz) {
        //         if (rz > 0.2f) {
        //             update(adapter, Direction.UP, footstepMP);
        //         } else if (rz < -0.2f) {
        //             update(adapter, Direction.DOWN, footstepMP);
        //         }
        //     }
        // });

        // accelerometer.setListener(new Accelerometer.Listener() {
        //     @Override
        //     public void onTranslation(float tx, float ty, float tz) {
        //         if (tx > 0.2f) {
        //             update(adapter, Direction.LEFT, footstepMP);
        //         } else if (tx < -0.2f) {
        //             update(adapter, Direction.RIGHT, footstepMP);
        //         }
        //     }
        // });

        gridview.setAdapter(adapter);
    }

    private void openMenu() {
        Intent intent = new Intent(this, LevelMenuActivity.class);
        startActivity(intent);
    }

    private void update(ImageAdapter adapter, Direction direction, MediaPlayer mp) {
        sokoban.game.move(direction);
        mp.start();
        adapter.updateGrid(sokoban.getView());
        NewText.setText(sokoban.getLevelInfo());

        if (sokoban.game.currentLevel.getTargetCount() == sokoban.game.currentLevel.getCompletedCount()) {
            levelCompleted();
        }

    }

    private void levelCompleted() {
        if (count == 0) {
            TitleText.setText("Level completed!");
            TitleText.setTextColor(Color.parseColor("#11ab3b"));
            TitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            mp.start();
            pauseChronometer();
            count++;
        }
    }

    private int[] getTouchedCoords(int index) {
        int columns = sokoban.game.currentLevel.getWidth();
        int x = index % columns;
        int y = index / columns;

        int[] indexCoords = {y, x};

        return indexCoords;
    }

    @Override
    protected void onResume() {
        super.onResume();

        accelerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        accelerometer.unregister();
        gyroscope.unregister();
    }

    public void startChronometer(View v) {
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pause);
            chronometer.start();
            isRunning = true;
        }
    }
    public void pauseChronometer() {
        if (isRunning) {
            chronometer.stop();
            pause = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
        }
    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pause = 0;
    }

}