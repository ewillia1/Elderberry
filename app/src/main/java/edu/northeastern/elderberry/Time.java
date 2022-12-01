package edu.northeastern.elderberry;

import android.util.Log;

public class Time {
    private static final String TAG = "MyTime";
    private String time;

    public Time(String time) {
        Log.d(TAG, "_____Time");
        this.time = time;
    }

    public String getTime() {
        Log.d(TAG, "_____getTime");
        return this.time;
    }

    public void setTime(String time) {
        Log.d(TAG, "_____setTime");
        this.time = time;
    }
}
