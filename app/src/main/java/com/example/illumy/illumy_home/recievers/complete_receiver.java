package com.example.illumy.illumy_home.recievers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.illumy.illumy_home.float_activity;

/**
 * Created by vidu on 29/4/17.
 */

public class complete_receiver extends BroadcastReceiver {
    String ALARM_ACTION_NAME = "com.illumy.action.alarm";
    String ACTION_REPEAT_FLOAT = "com.illumy.action.float_repeat";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(ALARM_ACTION_NAME);
        Intent intent1 = new Intent(ACTION_REPEAT_FLOAT);
        Intent service_intent = new Intent(context, float_activity.class);
        context.startService(service_intent);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 2, intent1, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(10*60*1000), pendingIntent1);
        context.sendBroadcast(intent2);
        Log.w("this","RESTARTED!!");

    }
}
