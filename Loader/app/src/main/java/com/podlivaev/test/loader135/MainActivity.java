package com.podlivaev.test.loader135;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private static final String LOG_TAG = "::ActivityLogs";
    static final int LOADER_TIME_ID = 1;

    TextView tvTime;
    RadioGroup rgTimeFormat;
    static int lastCheckedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView) findViewById(R.id.tvTime);
        rgTimeFormat = (RadioGroup) findViewById(R.id.rgTimeFormat);

        Bundle bndl = new Bundle();
        bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
        getLoaderManager().initLoader(LOADER_TIME_ID, bndl, this);

        lastCheckedId = rgTimeFormat.getCheckedRadioButtonId();

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    public Loader<String> onCreateLoader(int id, Bundle args){
        Loader<String> loader = null;
        if(id == LOADER_TIME_ID){
            loader = new TimeLoader(this,args);
            Log.d(LOG_TAG, "onCreateLoader: " + hashCode());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader,String result){
        Log.d(LOG_TAG, "onLoadFinished " + hashCode() + " result = " + result);
        tvTime.setText(result);
    }

    @Override
    public void onLoaderReset(Loader<String> loader){
        Log.d(LOG_TAG, "onLoaderReset " + hashCode());
    }

    public void getTimeClick(View view){
        Loader<String> loader;

        int id = rgTimeFormat.getCheckedRadioButtonId();
        if(id == lastCheckedId){
            loader = getLoaderManager().getLoader(LOADER_TIME_ID);
        }
        else{
            Bundle bndl = new Bundle();
            bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
            loader = getLoaderManager().restartLoader(LOADER_TIME_ID, bndl, this);
            lastCheckedId = id;
        }
        loader.forceLoad();
    }

    String getTimeFormat(){
        String result = TimeLoader.TIME_FORMAT_SHORT;
        switch(rgTimeFormat.getCheckedRadioButtonId()){
            case R.id.rdShort:
                result = TimeLoader.TIME_FORMAT_SHORT;
                break;
            case R.id.rdLong:
                result = TimeLoader.TIME_FORMAT_LONG;
                break;
        }
        return result;
    }

    public void observerClick(View view){
        Log.d(LOG_TAG, "observerClick");
        Loader<String> loader = getLoaderManager().getLoader(LOADER_TIME_ID);

        final ContentObserver observer = loader.new ForceLoadContentObserver();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                observer.dispatchChange(false, null);
            }
        },5000);

    }

}
