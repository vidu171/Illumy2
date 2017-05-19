package com.example.illumy.illumy_home.recievers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import com.example.illumy.illumy_home.database.profiledb_helper;
import com.example.illumy.illumy_home.services.fabview_service;
import com.example.illumy.illumy_home.profiles.profile_class;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.AM;

/**
 * Created by vidu on 26/4/17.
 */

public class alarm_receiver extends BroadcastReceiver {
    String ALARM_ACTION_NAME = "com.illumy.action.alarm";
    String ALARM_ACTION_NAME1 = "com.illumy.action.silent_1";
    String ALARM_ACTION_NAME2 = "com.illumy.action.silent_2";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service_intent = new Intent(context, fabview_service.class);
        context.startService(service_intent);
        if(ALARM_ACTION_NAME1.equals(intent.getAction())){
           // get a second before current time
            long t = System.currentTimeMillis();
            t = t - 40000;


            // get a time just less then the current time but greater than time t
            profiledb_helper dbhelper = new profiledb_helper(context);
            ArrayList<profile_class> profile_list = dbhelper.getList();
            long min_time = Long.parseLong("2493398320528");
            String Mode = new String();
            long ti =0;
            for(profile_class x: profile_list){
                if(x.Time_in_today()< min_time && x.Time_in_today()>=t){
                    min_time = x.Time_in_today();
                    Mode = x.Mode;
                }
            }
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(min_time);


            Log.w("mode :",Mode +" time :" + c.getTime().toString() );
            if(Mode.equals("SILENT")) {
                Toast.makeText(context, "illumy set the phone to Silent", Toast.LENGTH_LONG).show();
                AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 0 , 0);

            }
            else if (Mode.equals("RING")){

                Toast.makeText(context, "illumy set the phone to Ring", Toast.LENGTH_LONG).show();
                AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_RING, 100, 0);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 100 , 0);

            }

            Intent intent2 = new Intent(ALARM_ACTION_NAME);
            context.sendBroadcast(intent2);


        }

        if(ALARM_ACTION_NAME.equals(intent.getAction())||ALARM_ACTION_NAME2.equals(intent.getAction())) {


            profiledb_helper dbhelper = new profiledb_helper(context);
            ArrayList<profile_class> profile_list = dbhelper.getList();
            Calendar k2 = Calendar.getInstance();
            //Log.w("list item 2",String.valueOf(k2.getTime()));
            long current_time = System.currentTimeMillis();
            long min_time = Long.parseLong("2493398320528");
            String Mode1 = new String();
            for(profile_class x: profile_list){
                if(x.Time_in_today()< min_time && x.Time_in_today()>current_time){

                    min_time = x.Time_in_today();
                    Mode1 = x.Mode;
                }
            }
            Calendar k = Calendar.getInstance();
                    k.setTimeInMillis(min_time);
                //k.set(Calendar.YEAR,1997);

            Log.w("okk", k.getTime()+" "+Mode1+ " "+System.currentTimeMillis());
                            Intent i = new Intent(ALARM_ACTION_NAME1);

                PendingIntent intent1 = PendingIntent.getBroadcast(context, 2, i, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, min_time, intent1);
            Intent i2 = new Intent(ALARM_ACTION_NAME2);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(current_time);
            c.set(Calendar.HOUR,24);
            c.set(Calendar.AM_PM, AM);
            c.set(Calendar.MINUTE, 00);
            c.set(Calendar.SECOND, 10);

            Log.w("okk", c.getTime()+" "+c.getTimeInMillis());
            if(c.getTimeInMillis()>System.currentTimeMillis()) {
                PendingIntent intent2 = PendingIntent.getBroadcast(context, 2, i2, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), intent2);
            }
        }

    }
}
