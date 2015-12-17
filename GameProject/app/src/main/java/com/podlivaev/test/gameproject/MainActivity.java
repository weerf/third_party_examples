package com.podlivaev.test.gameproject;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMetrics = MainActivity.this.getResources().getDisplayMetrics();

      //  tileView = (GridView) findViewById(R.id.gridView);
      //  tileView.setAdapter(new TileAdapter(this));

        starView = (GridView) findViewById(R.id.starView);
   //     starView.setAdapter(new LineAdapter(this, 3, R.drawable.star,R.drawable.star_form));

        backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setScaleType(ImageView.ScaleType.CENTER);
        backImage.setImageResource(R.drawable.folga);

        chocolateTiles = new ChocolateTiles();

        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        starList = new LinkedList<ImageView>();
        createViews();

    }

    public void addStar(int pos){

        ImageView i = chocolateTiles.imIndexes[pos];
        Log.d(LOG_TAG,"imageView hash " + i.toString());
        int [] xy = new int[2];
        i.getLocationOnScreen(xy);

        ImageView imageView = new ImageView(this);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

       // lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        //lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
       // lp.setMargins(pxToDp(800 - 150 * starList.size()), pxToDp(50), 0, 0);
        imageView.setLayoutParams(lp);

        imageView.setImageResource(R.drawable.star_000);

        rootLayout.addView(imageView,lp);

        Log.d(LOG_TAG, "find star at " + pos + " coordinates are: " + xy[0] + ":" + xy[1]);


        starList.add(imageView);
        TranslateAnimation anim = new TranslateAnimation(
                Animation.ABSOLUTE, xy[0],
                Animation.ABSOLUTE, pxToDp(800 - 150 *(starList.size())),
                Animation.ABSOLUTE, pxToDp(xy[1]),
                Animation.ABSOLUTE, pxToDp(50));

        anim.setDuration(1000);
        anim.setFillAfter(false);
        imageView.startAnimation(anim);
        rootLayout.postDelayed(new FireStars(), 1000);

    }


    public void createViews(){
        int num = 0;
        for(int y = 0 ; y < 6; y++){
            for(int x=0; x < 4; x++) {
                ImageView imageView = new ImageView(this);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                int choco_size = 125;
                 Log.d(LOG_TAG, "set bounds are: " +
                                 (400 + choco_size * (x - 2)) + "  " +
                                 pxToDp(400 + choco_size * (x - 2)) + ":" +
                                 pxToDp(600 + choco_size * (y - 3))
                 );
                lp.setMargins(
                        pxToDp(400 + choco_size * (x - 2)),
                        pxToDp(600 + choco_size * (y - 3)),
                        0, 0);
             //   lp.setMarginStart(pxToDp(400 + choco_size* (x - 2)));

              //  imageView.setLayoutParams(lp);


                imageView.setImageResource(chocolateTiles.topImages[num]);
                imageView.setBackgroundResource(chocolateTiles.tileIndexes[num]);
                chocolateTiles.imIndexes[num] = imageView;

                rootLayout.addView(imageView, lp);
                imageView.setOnClickListener(new CListener(num));
                imageView.setSoundEffectsEnabled(false);


                int [] coord = new int[2];
                imageView.getLocationOnScreen(coord);
                Log.d(LOG_TAG, "bounds are: " + coord[0] + ":" + coord[1]);
                num++;


            }
        }
    }

    class CListener  implements View.OnClickListener{
        public CListener(int id){
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            if(chocolateTiles.doAnimation)
                return;

            Log.d(LOG_TAG, "click = " + id);

            chocolateTiles.doAnimation = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageDrawable(chocolateTiles.animationDrawable);

            v.postDelayed(new AfterEat(id),
                    chocolateTiles.animationDrawable.getDuration(0) *
                    chocolateTiles.animationDrawable.getNumberOfFrames());
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
            chocolateTiles.doAnimation = false;
            chocolateTiles.topImages[pos] = R.drawable.empty;
            if (chocolateTiles.tileIndexes[pos] == R.drawable.star_shape)
                addStar(pos);
        }

        int pos;
    }

    class ChocolateTiles{
        public ChocolateTiles() {
            tileIndexes = new Integer[SIZE];
            topImages = new Integer[SIZE];
            imIndexes = new ImageView[SIZE];

            for (int i = 0; i < SIZE; i++) {
                tileIndexes[i] = R.drawable.empty;
                topImages[i] = R.drawable.chocoladka_000;
            }

            for (int i = 0; i < 18; i++) {
                Random rn = new Random();
                int n = rn.nextInt(SIZE);
                if (tileIndexes[n] != R.drawable.empty)
                    i--;
                else {
                    tileIndexes[n] = R.drawable.star_shape;
                }
            }

            animationDrawable = (AnimationDrawable)
                    ContextCompat.getDrawable(MainActivity.this, R.drawable.eat);
            doAnimation = false;

        }

        public Integer[] topImages;
        public Integer[] tileIndexes;
        public ImageView[] imIndexes;
        public boolean doAnimation;
        public AnimationDrawable animationDrawable;
        private static final int SIZE = 4*6;
    }


    class FireStars implements Runnable{
        @Override
        public void run() {

            rootLayout.removeView(starList.get(starList.size() -1));

            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            int n = (800 - 150 * starList.size());
            Log.d(LOG_TAG, "abs=" + n +
                    "pxToDp=" + pxToDp(800 - 150 * starList.size())    );


            layoutParams.setMargins(pxToDp(800 - 150 * starList.size()), pxToDp(50), 0, 0);

            starList.get(starList.size() -1).setLayoutParams(layoutParams);
            rootLayout.addView(starList.get(starList.size() -1), layoutParams);


            int i = 1;
            for(ImageView star:starList){
                star.setImageResource(R.drawable.star_000);

                int [] coord = new int[2];
                star.getLocationOnScreen(coord);
                Log.d(LOG_TAG, "bounds are: " + coord[0] + ":" + coord[1]);

            }

            if (starList.size() == 5) {
                showPrize();
            }
        }
    }

    private void showPrize() {



        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.win_frame);

        TextView winView = new TextView(newContext);

        winView.setText(R.string.win_caption);
        winView.setBackgroundResource(R.drawable.win_frame);

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        //layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rootLayout.addView(winView, layoutParams);

    }


    public void onClick(View view){
        showPrize();
    }

    public int pxToDp(int dp) {
        Log.d(LOG_TAG,"xdpi:"+displayMetrics.xdpi );
        Log.d(LOG_TAG,"DM=" + DisplayMetrics.DENSITY_DEFAULT + " dp=" + dp +"*" + displayMetrics.xdpi);
        return Math.round(dp *((float) DisplayMetrics.DENSITY_DEFAULT) / displayMetrics.xdpi);
    }

    GridView tileView;
    GridView starView;
    List<ImageView> starList;
    ImageView backImage;
    RelativeLayout rootLayout;
    ChocolateTiles chocolateTiles;
  //  int starNumber;
    DisplayMetrics displayMetrics;

    private static final String LOG_TAG = "::game_activity";

}
