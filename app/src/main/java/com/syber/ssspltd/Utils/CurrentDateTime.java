package com.syber.ssspltd.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDateTime {
    public static String getCurrentDateString() {
        Date date = new Date();
        String strDateFormat = "yyyy/MM/dd";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static String getCurrentDateMMDDYYY(String date) {
//        Date date = new Date();
        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static String getCurrentDateStringDDMMYYYY() {
        Date date = new Date();
        String strDateFormat = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static String getCurrentDateDDMMYYY() {
        Date date = new Date();
        String strDateFormat = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
