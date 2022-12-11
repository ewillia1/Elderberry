package edu.northeastern.elderberry.util;

import android.util.Log;
import android.widget.Switch;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DatetimeFormat {
    private static final String TAG = "util.DatetimeFormat";

    public static String makeDateString(int day, int month, int year) {
        Log.d(TAG, "_____makeDateString");
        return getMonthFormat(month) + " " + day + ", " + year;
    }


    public static int getDateCompoFromString(String date, String compoType) {
        String[] words = splitDateByWords(date);
        int i = 0;
        for (String word : words) {
            Log.d(TAG, "makeStringDate: word " + i + " = " + word);
            i++;
        }

        if (compoType == "year") return  Integer.parseInt(words[2]);
        else if (compoType == "month") return  getMonthNumber(words[0]);
        else if (compoType == "dayOfMonth") return  Integer.parseInt(words[1]);
        else return 0;
    }

    public static Calendar makeStringDate(String date) {
        Log.d(TAG, "_____makeStringDate");
        String[] words = splitDateByWords(date);
        int i = 0;
        for (String word : words) {
            Log.d(TAG, "makeStringDate: word " + i + " = " + word);
            i++;
        }
        int month = getMonthNumber(words[0]);
        int day = Integer.parseInt(words[1]);
        int year = Integer.parseInt(words[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year + 1900, month, day);

        return calendar;
    }

    // https://www.javacodeexamples.com/java-split-string-by-words-example/1462
    private static String[] splitDateByWords(String str) {
        // If the string is empty or null, return an empty array.
        if (str == null || str.equals("")) {
            return new String[0];
        }

        // Split the string up by commas and spaces -- assuming the String comes into this method
        // in the format: MONTH DAY, YEAR
        return str.split("[ !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]+");
    }

    // Translate date selected to month.
    private static String getMonthFormat(int month_index) {
        int month = month_index + 1;
        Log.d(TAG, "_____getMonthFormat");
        if (month == 1) return "JAN";
        if (month == 2) return "FEB";
        if (month == 3) return "MAR";
        if (month == 4) return "APR";
        if (month == 5) return "MAY";
        if (month == 6) return "JUN";
        if (month == 7) return "JUL";
        if (month == 8) return "AUG";
        if (month == 9) return "SEP";
        if (month == 10) return "OCT";
        if (month == 11) return "NOV";
        if (month == 12) return "DEC";

        // Default should never happen.
        return "JAN";
    }

    private static int getMonthNumber(String month_name) {
        Log.d(TAG, "_____getMonthNumber");
        if (month_name.equals("JAN")) return 1;
        if (month_name.equals("FEB")) return 2;
        if (month_name.equals("MAR")) return 3;
        if (month_name.equals("APR")) return 4;
        if (month_name.equals("MAY")) return 5;
        if (month_name.equals("JUN")) return 6;
        if (month_name.equals("JUL")) return 7;
        if (month_name.equals("AUG")) return 8;
        if (month_name.equals("SEP")) return 9;
        if (month_name.equals("OCT")) return 10;
        if (month_name.equals("NOV")) return 11;
        if (month_name.equals("DEC")) return 12;

        // Default should never happen.
        return 1;
    }

    public static int dateDiff(Calendar o1, Calendar o2) {
        Log.d(TAG, "_____dateDiff");
        long diffInMillies = Math.abs(o1.getTimeInMillis() - o2.getTimeInMillis());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
