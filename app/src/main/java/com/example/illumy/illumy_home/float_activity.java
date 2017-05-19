package com.example.illumy.illumy_home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vidu on 8/5/17.
 */

public class float_activity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
