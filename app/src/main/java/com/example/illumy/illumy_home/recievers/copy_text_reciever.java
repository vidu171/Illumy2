package com.example.illumy.illumy_home.recievers;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

/**
 * Created by vidu on 9/5/17.
 */

public class copy_text_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        String email = intent.getStringExtra("EMAIL");
        String black = intent.getStringExtra("BLACK");
        String text = intent.getStringExtra("GETTEXT");
        Log.w("reciever"," "+email+" "+black+" "+" "+text);
        BackgroundMail.newBuilder(context)
                .withUsername(email)
                .withPassword(black)
                .withMailto("toemail@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("illumy")
                .withBody(text)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context,"EMAIL SENT",Toast.LENGTH_LONG).show();
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(context,"Unable to send Email",Toast.LENGTH_LONG).show();
                        //do some magic
                    }
                })
                .send();
    }
}
