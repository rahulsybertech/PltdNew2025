package com.syber.ssspltd.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static String formatDateTimeDDMMYYYY(String input) {
        // Input format
        String[] inputFormats = {
                "yyyy-MM-dd'T'HH:mm:ss",        // e.g., 2025-04-10T17:44:23
                "yyyy-MM-dd HH:mm:ss.SSS"       // e.g., 2025-04-10 17:44:23.176
        };
      //  SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        // Output format
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault());

        for (String format : inputFormats) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.getDefault());
                Date date = inputFormat.parse(input);
                if (date != null) {
                    return outputFormat.format(date);
                }
            } catch (ParseException e) {
                // Try the next format
            }
        }

        return "";
    }



    private void parseTime(String time12HourFormat) {
        try {
            // Convert 12-hour format to 24-hour format
            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

            Date date = inputFormat.parse(time12HourFormat); // Parse input time
            String time24HourFormat = outputFormat.format(date); // Convert to 24-hour format

            // Extract hour, minute, and second
            String[] timeParts = time24HourFormat.split(":");
            int checkOutHour = Integer.parseInt(timeParts[0]);  // 24-hour format hour
            int checkOutMinute = Integer.parseInt(timeParts[1]); // Minute
            int checkOutSecond = Integer.parseInt(timeParts[2]); // Second

            // Print values
            System.out.println("Hour: " + checkOutHour);
            System.out.println("Minute: " + checkOutMinute);
            System.out.println("Second: " + checkOutSecond);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
