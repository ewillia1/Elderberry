package edu.northeastern.elderberry.util;

import android.util.Log;

public class DatetimeFormat {
    private static final String TAG = "util.DatetimeFormat";

    public static String makeDateString(int day, int month, int year) {
        Log.d(TAG, "_____makeDateString");
        return getMonthFormat(month) + " " + day + ", " + year;
    }

    // Translate date selected to month
    private static String getMonthFormat(int month_index) {
        int month = month_index + 1;
        Log.d(TAG, "_____getMonthFormat");
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
}
