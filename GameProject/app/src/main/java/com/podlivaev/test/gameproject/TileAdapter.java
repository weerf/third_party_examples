package com.podlivaev.test.gameproject;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Random;

public class TileAdapter extends BaseAdapter {

    public TileAdapter (MainActivity c){
        context = c;

        tileIndexes = new Integer[SIZE];
        topImages = new Integer[SIZE];
        imIndexes = new ImageView[SIZE];

        for(int i=0;i < SIZE;i++){
            tileIndexes[i] = R.drawable.empty;
            topImages[i] = R.drawable.choco_000;
        }

        for(int i = 0; i < 15; i++) {
            Random rn = new Random();
            int n = rn.nextInt(SIZE);
            if(tileIndexes[n] !=  R.drawable.empty)
                i--;
            else{
                tileIndexes[n] = R.drawable.star_shape;
            }
        }

        animationDrawable = (AnimationDrawable)
                    ContextCompat.getDrawable(context, R.drawable.eat);
        doAnimation = false;


    }

    public int getCount(){
        return SIZE;
    }

    public Object getItem(int position){
        return  imIndexes[position];
    }

    public long getItemId(int position){
        return position + 1 ;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setSoundEffectsEnabled(false);
            imageView.setOnClickListener(new CListener(position));
        }
        else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(topImages[position]);

        imageView.setBackgroundResource(tileIndexes[position]);

        imIndexes[position] = imageView;


        return imageView;
    }

    class CListener  implements View.OnClickListener{
        public CListener(int id){
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            if(doAnimation)
                return;

            Log.d(LOG_TAG, "click = " + id);

            doAnimation = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageDrawable(animationDrawable);

            v.postDelayed(new AfterEat(id), animationDrawable.getDuration(0) * animationDrawable.getNumberOfFrames());
            imageView.setOnClickListener(null);
        }
        int id;
    }

    class AfterEat implements Runnable {
        public AfterEat(int id) {
            this.pos = id;
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "animation ends " + pos);
            doAnimation = false;
            topImages[pos] = R.drawable.empty;
            if (tileIndexes[pos] == R.drawable.star_shape)
                context.addStar(pos);
        }

        int pos;
    }


    public Integer[] tileIndexes;
    public Integer[] topImages;

    public ImageView[] imIndexes;

    private MainActivity context;

    private static final int SIZE = 4*6;
    private static final String LOG_TAG = "::adapter";

    private AnimationDrawable animationDrawable;
    private boolean doAnimation;


}
