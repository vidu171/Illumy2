package com.example.illumy.illumy_home.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.illumy.illumy_home.R;

/**
 * Created by vidu on 9/5/17.
 */

public class copy_text_service extends Service implements ClipboardManager.OnPrimaryClipChangedListener{
    ClipboardManager clipBoard;
    @Nullable

    Intent inten ;
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        Log.w("this","service start");
        clipBoard.addPrimaryClipChangedListener(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {

        Toast.makeText(this, "Service stopped...", Toast.LENGTH_SHORT).show();

        clipBoard.removePrimaryClipChangedListener(this);

    }



    @Override
    public void onPrimaryClipChanged() {
        Log.w("this","copied");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        //Todo: Do not forget to change the Email

        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"viduuj@gmail.com"} );
        intent.putExtra(Intent.EXTRA_TEXT,clipBoard.getText().toString());

        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification = new Notification.Builder(this).setContentTitle("Tap to Send the Text as clipboard")
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.drawable.icon).build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, notification);
        }
        Toast.makeText(getBaseContext(),"Text copied",Toast.LENGTH_LONG).show();

    }
}
