package com.example.illumy.illumy_home.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.illumy.illumy_home.profiles.profile_class;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vidu on 28/4/17.
 */

public class profiledb_helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "profile_list";
    private static final String TABLE_PROFILE = "profile";
    private static final String TYPE = "type";
    private static final String KEY_ID = "id";
    private static final String time_in_unix = "time";

    public profiledb_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILE_TABLE = "CREATE TABLE "+ TABLE_PROFILE + "("+KEY_ID +  " INTEGER PRIMARY KEY," +time_in_unix
                + " TEXT," +TYPE + " TEXT"+ " )";
        db.execSQL(CREATE_PROFILE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);

        // Create tables again
        onCreate(db);
    }

    public void ad_profile(profile_class profile){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(time_in_unix, String.valueOf(profile.unix_time));
        values.put(TYPE, profile.Mode);

        db.insert(TABLE_PROFILE, null,values);
        db.close();

    }

    public profile_class getProfile(int ID){
        SQLiteDatabase db = this.getReadableDatabase();

        Log.w("getPreofile id= ",String.valueOf(ID));
        Cursor cur = db.query(TABLE_PROFILE, new String[]{time_in_unix,TYPE},KEY_ID + "=?", new String[]{String.valueOf(ID)},
                null,null,null,null);
        if(cur !=null){
            cur.moveToFirst();
        }

        profile_class profile = new profile_class(Long.parseLong(cur.getString(0)),cur.getString(1));
        Log.w("getPreofile id= ","fine");
        return profile;
    }
    public void remove_profile(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PROFILE, KEY_ID + "=?",new String[]{String.valueOf(ID)});
        db.close();

    }
    public void remove_profile_with_time(long time){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PROFILE, time_in_unix + "=?",new String[]{String.valueOf(time)});
        db.close();

    }
    public ArrayList<profile_class> getList(){

        ArrayList<profile_class> profile_list = new ArrayList<>();
        String selectquery = "SELECT *FROM "+TABLE_PROFILE;
        //profile_list.add(new profile_class(00, "AAA"));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectquery,null);
        if(cursor.moveToFirst()){
            do{

                long time = Long.parseLong(cursor.getString(1));
                String mode = cursor.getString(2);
                profile_class profile_cl = new profile_class(time, mode);

                profile_list.add(profile_cl);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return profile_list;
    }
    public  void updatedb(){

        String selectquery = "SELECT *FROM "+TABLE_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectquery,null);
        int i =0;
        if(cursor.moveToFirst()){
            do{ profile_class current_profile = getProfile(cursor.getInt(0));

                Log.w("update_db",String.valueOf(current_profile.unix_time));
                ContentValues values = new ContentValues();
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal2.setTimeInMillis(System.currentTimeMillis());
                cal1.setTimeInMillis(current_profile.unix_time);
                cal1.set(Calendar.DATE, cal2.get(Calendar.DATE));
                cal1.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
                cal1.set(Calendar.YEAR, cal2.get(Calendar.YEAR));

                values.put(time_in_unix, cal1.getTimeInMillis() );
                db.update(TABLE_PROFILE, values, time_in_unix + " = ?",
                        new String[] { String.valueOf(current_profile.unix_time) });

            }
            while (cursor.moveToNext());
        }

        db.close();

    }
}
