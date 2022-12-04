package edu.northeastern.elderberry;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Medicine {
    private static final String TAG = "Medicine";
    private String name;
    private String information;
    private String fromDate;
    private String toDate;
    private String unit;
    private List taken;
    private List time;

    Medicine() {};

    public Medicine(String name, String information, String fromDate, String toDate, String unit) {
        Log.d(TAG, "_____Medicine");
        this.name = name;
        this.information = information;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
        this.taken = new ArrayList<>();
        this.time = new ArrayList<>(Arrays.asList("NOV 15, 2022", "NOV 16, 2022", "NOV 17, 2022", "NOV 18, 2022", "NOV 19, 2022", "NOV 20, 2022", "NOV 21, 2022", "NOV 22, 2022", "NOV 23, 2022", "NOV 24, 2022", "NOV 25, 2022", "NOV 26, 2022"));
    }

    //public Medicine(String name, String information, String fromDate, String toDate, String unit, ArrayList taken, ArrayList time) {
    //    this.name = name;
    //    this.information = information;
    //    this.fromDate = fromDate;
    //    this.toDate = toDate;
    //    this.unit = unit;
    //    this.taken = taken;
    //    this.time = time;
    //}

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
        Log.d(TAG, "_____getUnit");
        return this.unit;
    }

    public List getTaken() {
        return this.taken;
    }

    public List getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", unit='" + unit + '\'' +
                //", taken=" + Arrays.toString(taken) +
                //", time=" + Arrays.toString(time) +
                '}';
    }
}
