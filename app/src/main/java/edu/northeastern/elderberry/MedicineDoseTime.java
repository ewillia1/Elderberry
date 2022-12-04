package edu.northeastern.elderberry;

import java.util.List;
import java.util.Map;

public class MedicineDoseTime {

    private Map<String, List<String>> dose;
    private Map<String, List<String>> time;
    private String name;
    private String information;
    private String fromDate;
    private String toDate;
    private String unit;

    public MedicineDoseTime(Map<String, List<String>> dose, Map<String, List<String>> time, String name, String information, String fromDate, String toDate, String unit) {
        this.dose = dose;
        this.time = time;
        this.name = name;
        this.information = information;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
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

    @Override
    public String toString() {
        return "MedicineDoseTime{" +
                "dose=" + dose.toString() +
                ", time=" + time.toString() +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
