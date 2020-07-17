package com.azezara.sokobangame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelMenuActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.azezara.sokobangame.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.azezara.sokobangame.EXTRA_TEXT2";

    private Button levelOneBtn;
    private Button levelTwoBtn;
    private Button levelThreeBtn;
    private Button levelFourBtn;
    private Button levelFiveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        levelOneBtn = (Button) findViewById(R.id.buttonLevel1);
        levelTwoBtn = (Button) findViewById(R.id.buttonLevel2);
        levelThreeBtn = (Button) findViewById(R.id.buttonLevel3);
        levelFourBtn = (Button) findViewById(R.id.buttonLevel4);
        levelFiveBtn = (Button) findViewById(R.id.buttonLevel5);


        levelOneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLevelOne();
            }
        });

        levelTwoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLevelTwo();
            }
        });

        levelThreeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLevelThird();
            }
        });

        levelFourBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLevelFour();
            }
        });

        levelFiveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLevelFive();
            }
        });

    }

    private void openLevelOne() {
        //int levelNumber = 1;
        String levelTitle = "The first level";
        String levelMap = "######" +
                            "#+x+.#" +
                            "#..x.#" +
                            "#.w..#" +
                            "######";

        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(EXTRA_NUMBER, levelNumber); // Level Number
        intent.putExtra(EXTRA_TEXT, levelTitle); // Level Title
        intent.putExtra(EXTRA_TEXT2, levelMap); // Level Map String
        startActivity(intent);
    }

    private void openLevelTwo() {
        String levelTitle = "Second level";
        String levelMap = "######" +
                            "#....#" +
                            "#.x+.#" +
                            "#.x+w#" +
                            "######";

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT, levelTitle);
        intent.putExtra(EXTRA_TEXT2, levelMap);
        startActivity(intent);
    }


    private void openLevelThird() {
        String levelTitle = "The 3rd level";
        String levelMap = "######" +
                            "#....#" +
                            "#.wx+#" +
                            "#.x+.#" +
                            "######";

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT, levelTitle);
        intent.putExtra(EXTRA_TEXT2, levelMap);
        startActivity(intent);
    }

    private void openLevelFour() {
        String levelTitle = "Fourth level";
        String levelMap = "######" +
                "#w..+#" +
                "#.xx.#" +
                "#..+.#" +
                "######";

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT, levelTitle);
        intent.putExtra(EXTRA_TEXT2, levelMap);
        startActivity(intent);
    }

    private void openLevelFive() {
        String levelTitle = "Final level";
        String levelMap = "######" +
                "#w...#" +
                "#...x#" +
                "#.x++#" +
                "######";

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT, levelTitle);
        intent.putExtra(EXTRA_TEXT2, levelMap);
        startActivity(intent);
    }


}
