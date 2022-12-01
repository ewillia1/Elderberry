package edu.northeastern.elderberry;

import android.util.Log;

import androidx.annotation.NonNull;

public class Medicine {
    private static final String TAG = "Medicine";
    private final String name;
    private final String information;
    private final String fromDate;
    private final String toDate;
    private final String unit;

    public Medicine(String name, String information, String fromDate, String toDate, String unit) {
        this.name = name;
        this.information = information;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
    }

    public String getName() {
        Log.d(TAG, "_____getName");
        return this.name;
    }

    public String getInformation() {
        Log.d(TAG, "_____getInformation");
        return this.information;
    }

    public String getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return this.fromDate;
    }

    public String getToDate() {
        Log.d(TAG, "_____getToDate");
        return this.toDate;
    }

    public String getUnit() {
        return this.unit;
    }

    @NonNull
    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + this.name + '\'' +
                ", information='" + this.information + '\'' +
                ", fromDate='" + this.fromDate + '\'' +
                ", toDate='" + this.toDate + '\'' +
                ", unit='" + this.unit + '\'' +
                '}';
    }
}
