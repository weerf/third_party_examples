package com.podlivaev.test.pending119;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by kirill on 06.11.15.
 */
public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d(LOG_TAG, "onReceive");
        Log.d(LOG_TAG, "action==" + intent.getAction());
        Log.d(LOG_TAG, "extra==" + intent.getStringExtra("extra"));

    }

    private String LOG_TAG = "::pend";
}
