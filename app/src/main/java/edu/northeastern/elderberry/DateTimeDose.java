package edu.northeastern.elderberry;

import android.util.Log;

import java.util.Date;

public class DateTimeDose {
    private static final String TAG = "DateTimeDose";
    private Date fromTime;
    private Date toDate;
    private int dose;
    private String name;

    public DateTimeDose(Date fromTime, Date toDate, int dose, String name) {
        Log.d(TAG, "_____DateTimeDose");
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.dose = dose;
        this.name = name;
    }

    public DateTimeDose() {
        Log.d(TAG, "_____DateTimeDose");
    }

    public Date getFromTime() {
        Log.d(TAG, "_____getFromTime");
        return this.fromTime;
    }

    public void setFromTime(Date fromTime) {
        Log.d(TAG, "_____setFromTime");
        this.fromTime = fromTime;
    }

    public Date getToDate() {
        Log.d(TAG, "_____getToDate");
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        Log.d(TAG, "_____setToDate");
        this.toDate = toDate;
    }

    public int getDose() {
        Log.d(TAG, "_____getDose");
        return this.dose;
    }

    public void setDose(int dose) {
        Log.d(TAG, "_____setDose");
        this.dose = dose;
    }

    public String getName() {
        Log.d(TAG, "_____getName");
        return this.name;
    }

    public void setName(String name) {
        Log.d(TAG, "_____setName");
        this.name = name;
    }
}
