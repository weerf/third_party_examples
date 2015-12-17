package com.podlivaev.test.drawable162;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        setDrawable();

    }

    private void setDrawable(){

        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                new int[]{Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA }
                );

        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setStroke(10, Color.BLACK, 20, 5);
        drawable.setCornerRadius(30.f);

        imageView.setImageDrawable(drawable);

    }

    ImageView imageView;
    TextView textView;
}
