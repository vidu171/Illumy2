package com.example.illumy.illumy_home.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.illumy.illumy_home.R;

/**
 * Created by vidu on 7/5/17.
 */

public class fabview_service extends Service {
    String ACTION_REPAT_FLOAT = "com.illumy.action.float_repeat";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }
    public void onCreate(){
        final ImageView float_view = new ImageView(this);
        float_view.setImageResource(R.drawable.icon);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        final SharedPreferences.Editor editor = pref.edit();


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(90, 90,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.x = 200;
        params.y = 140;

        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(float_view,params);

        float_view.setOnTouchListener(new View.OnTouchListener() {
            int inital_x;
            int initial_y;
            float initialTouchX;
            float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        inital_x = params.x;
                        initial_y = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case  MotionEvent.ACTION_UP :
                        WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.TYPE_PHONE,
                                WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                                PixelFormat.TRANSLUCENT);
                        params2.x = 200;
                        params2.y = 300;
                        if((params.x<=inital_x+10&&params.x>=inital_x-10)&&(params.y<=initial_y+10&&params.y>=initial_y-10)) {
                        //final WindowManager windowManager2 = (WindowManager) getSystemService(WINDOW_SERVICE);
                        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(getBaseContext().LAYOUT_INFLATER_SERVICE);
                        final View mview = inflater.inflate(R.layout.float_view,null);
                        windowManager.removeView(float_view);
                        windowManager.addView(mview,params2);
                            ImageView float_close = new ImageView(getBaseContext());
                        float_close = (ImageView) mview.findViewById(R.id.close_window);
                            Switch copy_cliboard = (Switch) mview.findViewById(R.id.switch2);
                            final Intent service_intent = new Intent(getBaseContext(), copy_text_service.class);



                            copy_cliboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if(isChecked){
                                        editor.putString("copy_clipboard","running").commit();
                                        startService(service_intent);
                                    }
                                    else {
                                        editor.putString("copy_clipboard","not").commit();
                                        stopService(service_intent);
                                        Log.w("kk","trynna switch the zervice off");
                                    }
                                }
                            });
                            String switch_state = pref.getString("copy_clipboard",null);

                            if(switch_state == null){

                                editor.putString("copy_clipboard","not").commit();

                            }
                            switch_state = pref.getString("copy_clipboard",null);
                            Log.w("this",switch_state);
                            if(switch_state != null) {
                                if(switch_state.equals("running")) {
                                    copy_cliboard.setChecked(true);
                                    startService(service_intent);
                                    //startService()
                                }
                                else if(switch_state.equals("not")){
                                    copy_cliboard.setChecked(false);
                                    stopService(service_intent);
                                }
                            }

                            float_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    windowManager.removeView(mview);
                                    windowManager.addView(float_view, params);
                                }
                            });
                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = inital_x + (int)(event.getRawX() - initialTouchX);
                        params.y = initial_y + (int)(event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(float_view, params);
                        return true;
                }
                return false;
            }

        });
        Intent intent = new Intent(ACTION_REPAT_FLOAT);
        this.sendBroadcast(intent);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
