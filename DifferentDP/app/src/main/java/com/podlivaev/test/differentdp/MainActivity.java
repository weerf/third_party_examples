package com.podlivaev.test.differentdp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = metrics.heightPixels / density;
        float dpWidth  = metrics.widthPixels / density;

        String size = "w="+ dpWidth + "h="+dpHeight;

        Log.d("::tag", size);

    }

    public void onClickNext(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
