package com.example.illumy.illumy_home;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.illumy.illumy_home.database.profiledb_helper;
import com.example.illumy.illumy_home.profiles.profile_class;

import me.tankery.lib.circularseekbar.CircularSeekBar;

/**
 * Created by vidu on 26/4/17.
 */

public class sc_ring_activity extends AppCompatActivity {

    int hr_temp = 0;
    int min_temp = 0;
    String Mode = "";
    boolean AM_bool = true;
    String ALARM_ACTION_NAME = "com.illumy.action.alarm";


    String ALARM_ACTION_NAME2 = "com.illumy.action.silent_2";
    CircularSeekBar hour_bar ;
    CircularSeekBar minute_bar ;
    TextView hour_text;
    TextView minute_text ;
    Switch ampm ;

    Button setButton;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_ring_activity);
        Bundle bundle = getIntent().getExtras();
        Mode = bundle.getString("MODE");

        //settings of the circular bar
        hour_bar = (CircularSeekBar) findViewById(R.id.hour_bar);
        minute_bar = (CircularSeekBar) findViewById(R.id.minute_bar);
        hour_bar.setMax(12);
        hour_bar.setCircleProgressColor(getResources().getColor(R.color.colorAccent));
        hour_bar.setCircleStrokeWidth(10);
        hour_bar.setPointerColor(getResources().getColor(R.color.colorAccent));
        minute_bar.setPointerColor(getResources().getColor(R.color.colorAccent));
        minute_bar.setCircleStrokeWidth(10);
        minute_bar.setCircleProgressColor(getResources().getColor(R.color.colorAccent));
        minute_bar.setMax(60);
        hour_text = (TextView) findViewById(R.id.hour_text);
        minute_text = (TextView) findViewById(R.id.minute_text);

        //statusbar colour set
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#080A11"));
        }
        // On changing the values of the bars
        hour_bar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                    hr_temp = (int)hour_bar.getProgress();
                    hour_text.setText(String.valueOf(hr_temp));
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
        minute_bar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                min_temp = (int)minute_bar.getProgress();
                minute_text.setText(String.valueOf(min_temp));
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });

        // next button
        setButton = (Button) findViewById(R.id.setbutton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                    if(hr_temp == 0 && min_temp == 0) {
                        Toast.makeText(sc_ring_activity.this, "Pleaase set a time",Toast.LENGTH_SHORT).show();
                    }
                    else{
                    setAlarm(hr_temp, min_temp);
                }
            }
        });
        //cancelbutton command
        Button but = (Button) findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();



            }
        });
        ampm  = (Switch) findViewById(R.id.switch1);
        ampm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ampm.getText().equals("PM")) {
                    ampm.setText("AM");
                    AM_bool = true;
                }
                else {
                    ampm.setText("PM");
                    AM_bool = false;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(int hr, int min){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        Intent intent2 = new Intent(ALARM_ACTION_NAME);
        sendBroadcast(intent2);

        Calendar cal = Calendar.getInstance();
        if(AM_bool) {
            cal.set(Calendar.HOUR_OF_DAY, hr );

        }
        else {
            if(hr==12){
                cal.set(Calendar.HOUR_OF_DAY, hr );
            }
            else {
                cal.set(Calendar.HOUR_OF_DAY, hr + 12);
            }
        }
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);


        profiledb_helper db_helper = new profiledb_helper(this);
        db_helper.ad_profile(new profile_class(cal.getTimeInMillis(),Mode));
        Log.w("AM_PM", cal.getTime().toString());

        finish();
        Intent i = new Intent(sc_ring_activity.this, ring_profile_list.class);
        startActivity(i);



    }
}
