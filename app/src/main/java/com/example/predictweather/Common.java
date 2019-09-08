package com.example.predictweather;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID="f009e089ca62be88a16d974899f4b627";
    public static Location current_location=null;

    public static String convertUnixToDate(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String fomatted=simpleDateFormat.format(date);
        return fomatted;

    }

    public static String convertUnixToHour(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        String fomatted=simpleDateFormat.format(date);
        return fomatted;
    }
}
