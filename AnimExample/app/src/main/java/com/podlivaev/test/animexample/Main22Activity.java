package com.podlivaev.test.animexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Main22Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interpolator);



		animAccelerate = AnimationUtils.loadAnimation(this, R.anim.accelerate);

		animDecelerate = AnimationUtils.loadAnimation(this, R.anim.decelerate);

		animAccelerateDecelerate = AnimationUtils.loadAnimation(this,
				R.anim.accelerate_decelerate);

		animAnticipate = AnimationUtils.loadAnimation(this, R.anim.anticipate);

		animAnticipateOvershoot = AnimationUtils.loadAnimation(this,
				R.anim.anticipate_overshoot);

		animOvershoot = AnimationUtils.loadAnimation(this, R.anim.overshoot);

		animBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

		animCycle = AnimationUtils.loadAnimation(this, R.anim.cycle);

		animLinear = AnimationUtils.loadAnimation(this, R.anim.linear);

		image = (ImageView) findViewById(R.id.image);

    }


	public void onBClick(View v) {
		switch (v.getId()) {
		case R.id.accelerate:
			image.startAnimation(animAccelerate);
			break;
		case R.id.decelerate:
			image.startAnimation(animDecelerate);
			break;
		case R.id.acceleratedecelerate:
			image.startAnimation(animAccelerateDecelerate);
			break;
		case R.id.anticipate:
			image.startAnimation(animAnticipate);
			break;
		case R.id.anticipateovershoot:
			image.startAnimation(animAnticipateOvershoot);
			break;
		case R.id.overshoot:
			image.startAnimation(animOvershoot);
			break;
		case R.id.bounce:
			// image.startAnimation(animBounce);
			TranslateAnimation tAnim = new TranslateAnimation(0, 0, -700, 10);
			tAnim.setInterpolator(new BounceInterpolator());
			tAnim.setDuration(1000);

			image.startAnimation(tAnim);
			break;
		case R.id.cycle:
			image.startAnimation(animCycle);
			break;
		case R.id.linear:
			image.startAnimation(animLinear);
			break;
		}
	}



    ImageView image;
	Animation animAccelerate;
	Animation animDecelerate;
	Animation animAccelerateDecelerate;
	Animation animAnticipate;
	Animation animAnticipateOvershoot;
	Animation animOvershoot;
	Animation animBounce;
	Animation animCycle;
	Animation animLinear;


}
