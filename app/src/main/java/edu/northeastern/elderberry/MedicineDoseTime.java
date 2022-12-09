package edu.northeastern.elderberry;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class MedicineDoseTime {
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
    }

    public Map<String, List<String>> getDose() {
        return dose;
    }

    public void setDose(Map<String, List<String>> dose) {
        this.dose = dose;
    }

    public Map<String, List<String>> getTime() {
        return time;
    }

    public void setTime(Map<String, List<String>> time) {
        this.time = time;
    }

    public Map<String, List<Boolean>> getTaken() {
        return this.taken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getFreq() {
        return freq;
    }

    @NonNull
    @Override
    public String toString() {
        return "MedicineDoseTime{" +
                "dose=" + dose +
                ", time=" + time +
                ", taken=" + taken +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", unit='" + unit + '\'' +
                ", freq=" + freq +
                '}';
    }
}
