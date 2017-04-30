package com.example.illumy.illumy;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.illumy.illumy.database.profiledb_helper;
import com.example.illumy.illumy.profiles.profile_adapter;
import com.example.illumy.illumy.profiles.profile_class;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidu on 26/4/17.
 */

public class ring_profile_list extends AppCompatActivity {

    String ALARM_ACTION_NAME = "com.illumy.action.alarm";
    profile_adapter madapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_list);
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ListView listView = (ListView)findViewById(R.id.list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ShowAlertDialogWithListview();
            }
        });
        task taskee = new task();
        taskee.execute();
        ArrayList<profile_class> profile_classList = new ArrayList<>();

        madapter = new profile_adapter(this,profile_classList);
        listView.setAdapter(madapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profile_class profile = madapter.getItem(position);
                showdialog(profile);
            }
        });
        Button bb = (Button) findViewById(R.id.button2);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profiledb_helper hel = new profiledb_helper(ring_profile_list.this);
                hel.updatedb();
            }
        });
    }
    public class task extends AsyncTask<Void,Void,List<profile_class>>{

        @Override
        protected ArrayList<profile_class> doInBackground(Void... params) {
            profiledb_helper db_helper = new profiledb_helper(getApplicationContext());
            ArrayList<profile_class> profiles_list = db_helper.getList();

            return profiles_list;
        }
        protected void onPostExecute(List<profile_class> data) {
            madapter.clear();


            if (data != null && !data.isEmpty()) {
                madapter.addAll(data);
            }


        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void ShowAlertDialogWithListview() {
        List<String> modes = new ArrayList<String>();
        modes.add("SILENT");
        modes.add("RING");

        final CharSequence[] MODES = modes.toArray(new String[modes.size()]);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setTitle("Select Mode");
        dialogbuilder.setItems(MODES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(ring_profile_list.this, sc_ring_activity.class);
                i.putExtra("MODE", MODES[which].toString());
                startActivity(i);
                finish();

            }
        });
        dialogbuilder.show();
    }
    public  void showdialog(final profile_class profile ){
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setTitle("Want to delete");
        dialogbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                profiledb_helper db_helper = new profiledb_helper(getApplicationContext());
                db_helper.remove_profile_with_time(profile.unix_time);
                Intent intent2 = new Intent(ALARM_ACTION_NAME);
                sendBroadcast(intent2);
                recreate();

            }
        });
        dialogbuilder.show();
    }


    }
