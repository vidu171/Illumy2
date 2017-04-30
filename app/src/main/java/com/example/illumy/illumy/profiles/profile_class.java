package com.example.illumy.illumy.profiles;

import java.util.Calendar;

import static android.icu.util.Calendar.AM;
import static android.icu.util.Calendar.MINUTE;
import static android.icu.util.Calendar.PM;

/**
 * Created by vidu on 26/4/17.
 */

public class profile_class {
    int hourStart;
    int minuteStart;
    int ID;
    public  String Mode= new String();
    String ampm;
    public Long unix_time;

    public profile_class(long Unix , String type){
        unix_time = Unix;
        Mode = type;
    }
    public long Time_in_today(){
        Long t;
        Calendar c = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(System.currentTimeMillis());
        c.setTimeInMillis(unix_time);
        c.set(Calendar.DATE, cal2.get(Calendar.DATE));
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MONTH, cal2.get(Calendar.MONTH));
        c.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
        t = c.getTimeInMillis();
        return t;
    }


}
