package edu.northeastern.elderberry;

import android.util.Log;

import androidx.annotation.NonNull;

public class Medicine {
    private static final String TAG = "Medicine";
    private String id;
    private String name;
    private String information;
    private String fromDate;
    private String toDate;
    private String unit;

    Medicine() {}

    public Medicine(String id, String name, String information, String fromDate, String toDate, String unit) {
        Log.d(TAG, "_____Medicine");
        this.id = id;
        this.name = name;
        this.information = information;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
    }

    public String getId() {
        Log.d(TAG, "_____getId");
        return this.id;
    }

    public String getName() {
        Log.d(TAG, "_____getName");
        return this.name;
    }

    public String getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return this.fromDate;
    }

    public String getInformation() {
        Log.d(TAG, "_____getInformation");
        return this.information;
    }

    public String getUnit() {
        Log.d(TAG, "_____getUnit");
        return this.unit;
    }

    public String getToDate() {
        Log.d(TAG, "_____getToDate");
        return this.toDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
