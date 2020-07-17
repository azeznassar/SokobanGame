package com.azezara.sokobangame;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.MotionEvent;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList newView;
    int result = -1;

    public ImageAdapter(Context c, ArrayList v) {
        mContext = c;
        newView = v;
    }

    public int getCount() {
        return updateView().length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getId() {
        this.result++;
        return this.result;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setId(getId());
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185)); // 140 X2
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);

        } else {
            imageView = (ImageView) convertView;
        }

        Integer[] theLevel = updateView();
        imageView.setTag(theLevel[position]);
        imageView.setImageResource(theLevel[position]);

        return imageView;
    }

    public Integer[] updateView() {
        Integer[] level = new Integer[30];
        for (int i = 0; i < newView.size(); i++) {
            if ("playertile".equals(newView.get(i))) {
                level[i] = R.drawable.playertile;
            } else if ("cratetile".equals(newView.get(i))) {
                level[i] = R.drawable.cratetile;
            } else if ("targettile".equals(newView.get(i))) {
                level[i] = R.drawable.targettile;
            } else if ("emptytile".equals(newView.get(i))) {
                level[i] = R.drawable.emptytile;
            } else if ("walltile".equals(newView.get(i))) {
                level[i] = R.drawable.walltile;
            }
        }
        return level;
    }

    public void updateGrid(ArrayList updatedView) {
        newView.clear();
        newView = updatedView;
        this.notifyDataSetChanged();
    }

}