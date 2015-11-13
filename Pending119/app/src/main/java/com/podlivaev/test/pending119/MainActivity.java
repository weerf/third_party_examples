package com.podlivaev.test.pending119;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);


    }

    public void onClick1(View v){
        intent1 = createIntent("action", "extraA");
        pIntent1 = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, 0);

        intent2 = createIntent("action", "extraB");
        pIntent2 = PendingIntent.getBroadcast(MainActivity.this, 0, intent2, 0);


        Log.d(LOG_TAG, "URI 1:" + intent1.toUri(Intent.URI_ANDROID_APP_SCHEME));
        Log.d(LOG_TAG, "URI 2:" + intent2.toUri(Intent.URI_INTENT_SCHEME));


        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 4000, pIntent1);
        am.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 5000, pIntent2);
    }

    public void onClick2(View view){
/*
        intent2 = createIntent("action", "extra B");
        pIntent2 = PendingIntent.getBroadcast(MainActivity.this, 11, intent2, PendingIntent.FLAG_NO_CREATE);
        sendNotif(1002, pIntent2);
            if (pIntent2 == null) Log.d(LOG_TAG, "pIntent2 is null");
      else Log.d(LOG_TAG, "pIntent2 created");
      */


        pIntent2.cancel();


    }


    Intent createIntent(String action, String extra){
        Intent intent = new Intent(MainActivity.this, Receiver.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }

    void compare(){
        Log.d(LOG_TAG, "intent1 = intent2: " + intent1.filterEquals(intent2));
        Log.d(LOG_TAG, "pIntent1 = pIntent2: " + pIntent1.equals(pIntent2));
    }

    void sendNotif(int id, PendingIntent pi){

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title " + id)
                .setContentText("Content" + id)
                .setContentIntent(pi)
                .setAutoCancel(true);

        nm.notify(id, mBuilder.build());

    }

    Intent intent1;
    Intent intent2;

    PendingIntent pIntent1;
    PendingIntent pIntent2;

    NotificationManager nm;
    AlarmManager am;
    private String LOG_TAG = "::pend";
}
