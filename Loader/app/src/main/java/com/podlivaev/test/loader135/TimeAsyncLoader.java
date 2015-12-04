package com.podlivaev.test.loader135;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by kirill on 04.12.15.
 */
public class TimeAsyncLoader extends AsyncTaskLoader<String> {
    final static String LOG_TAG = "::asyncTask";
    final int PAUSE = 10;

    public static final String ARGS_TIME_FORMAT = "time_format";
    public static final String TIME_FORMAT_SHORT = "h:mm:ss a";
    public static final String TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss";

    String format;

    public TimeAsyncLoader(Context context, String f){
        super(context);
        Log.d(LOG_TAG, hashCode() + "create TimeAsyncLoader(())");
        format = f;
    }

    public String loadInBackground(){
        Log.d(LOG_TAG, hashCode() + "loadInBackground(()) start");
        try {
            TimeUnit.SECONDS.sleep(PAUSE);
        }
        catch (InterruptedException ex){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
         Log.d(LOG_TAG, hashCode() + "loadInBackground(()) ends");
        return sdf.format(new Date());
    }
}
