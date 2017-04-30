package com.example.illumy.illumy.profiles;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.illumy.illumy.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vidu on 26/4/17.
 */

public class profile_adapter extends ArrayAdapter<profile_class> {
    public profile_adapter(@NonNull Context context, @LayoutRes ArrayList<profile_class> resource) {
        super(context, 0,resource);
    }
    class ViewHolder {
        TextView time ;
        TextView Mode;
    }
    ViewHolder holder = new ViewHolder();


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list = convertView;


        profile_class currentObject = getItem(position);

        if(list == null){
            list = LayoutInflater.from(getContext()).inflate(R.layout.profile_box,parent,false);

        }

        holder.time = (TextView) list.findViewById(R.id.time);
        holder.Mode = (TextView) list.findViewById(R.id.type);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentObject.unix_time);
        String time = cal.getTime().toString();

        StringBuilder final_display = new StringBuilder();
        final_display.append(String.valueOf(cal.get(Calendar.HOUR)) +":" );
        if(cal.get(Calendar.MINUTE)<10){
            final_display.append("0");
        }
        final_display.append(String.valueOf(cal.get(Calendar.MINUTE))+" ");
        if(cal.get(Calendar.AM_PM)<1){
            final_display.append("AM");
        }
        else {
            final_display.append("PM");
        }

        final_display.append(" "+cal.get(Calendar.DATE));

        holder.time.setText(final_display.toString());
        //Log.w("thiss",String.valueOf(currentObject.unix_time));
        holder.Mode.setText(currentObject.Mode);
        list.setTag(holder);
        return list;
    }

}
