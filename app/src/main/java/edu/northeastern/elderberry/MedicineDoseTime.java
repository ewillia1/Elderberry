package edu.northeastern.elderberry;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class MedicineDoseTime {
    private static final String TAG = "MedicineDoseTime";
    private Map<String, List<String>> dose;
    private Map<String, List<String>> time;
    private Map<String, List<Boolean>> taken;
    private String name;
    private String information;
    private String fromDate;
    private String toDate;
    private String unit;
    private int freq;

    public MedicineDoseTime(Map<String, List<String>> dose, Map<String, List<String>> time, Map<String, List<Boolean>> taken, String name, String information, String fromDate, String toDate, String unit, int freq) {
        Log.d(TAG, "_____MedicineDoseTime");
        this.dose = dose;
        this.time = time;
        this.taken = taken;
        this.name = name;
        this.information = information;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
        this.freq = freq;
    }

    public MedicineDoseTime() {
        Log.d(TAG, "_____MedicineDoseTime");
    }

    public Map<String, List<String>> getDose() {
        Log.d(TAG, "_____getDose");
        return dose;
    }

    public void setDose(Map<String, List<String>> dose) {
        Log.d(TAG, "_____setDose");
        this.dose = dose;
    }

    public Map<String, List<String>> getTime() {
        Log.d(TAG, "_____getTime");
        return time;
    }

    public void setTime(Map<String, List<String>> time) {
        Log.d(TAG, "_____setTime");
        this.time = time;
    }

    public Map<String, List<Boolean>> getTaken() {
        Log.d(TAG, "_____getTaken");
        return this.taken;
    }

    public String getName() {
        Log.d(TAG, "_____getName");
        return this.name;
    }

    public void setName(String name) {
        Log.d(TAG, "_____setName");
        this.name = name;
    }

    public String getInformation() {
        Log.d(TAG, "getInformation: ");
        return this.information;
    }

    public void setInformation(String information) {
        Log.d(TAG, "____setInformation");
        this.information = information;
    }

    public String getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return this.fromDate;
    }

    public void setFromDate(String fromDate) {
        Log.d(TAG, "_____setFromDate");
        this.fromDate = fromDate;
    }

    public String getToDate() {
        Log.d(TAG, "_____getToDate");
        return toDate;
    }

    public void setToDate(String toDate) {
        Log.d(TAG, "_____setToDate");
        this.toDate = toDate;
    }

    public String getUnit() {
        Log.d(TAG, "_____getUnit");
        return this.unit;
    }

    public void setUnit(String unit) {
        Log.d(TAG, "_____setUnit");
        this.unit = unit;
    }

    public int getFreq() {
        Log.d(TAG, "_____getFreq");
        return freq;
    }

    @NonNull
    @Override
    public String toString() {
        return "MedicineDoseTime{" +
                "dose=" + this.dose +
                ", time=" + this.time +
                ", taken=" + this.taken +
                ", name='" + this.name + '\'' +
                ", information='" + this.information + '\'' +
                ", fromDate='" + this.fromDate + '\'' +
                ", toDate='" + this.toDate + '\'' +
                ", unit='" + this.unit + '\'' +
                ", freq=" + this.freq +
                '}';
    }
}
