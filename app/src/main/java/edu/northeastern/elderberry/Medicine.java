package edu.northeastern.elderberry;

import androidx.annotation.NonNull;

public class Medicine {
    private String name;
    //private String dose;
    private String fromDate;
    private String toDate;
    //private String time1;
    //private String time2;
    //private String time3;
    //private String time4;
    //private String time5;
    //private String time6;
    //private String time7;
    //private String time8;
    //private String time9;
    //private String time10;
    //private String time11;
    //private String time12;
    //private String dose1;
    //private String dose2;
    //private String dose3;
    //private String dose4;
    //private String dose5;
    //private String dose6;
    //private String dose7;
    //private String dose8;
    //private String dose9;
    //private String dose10;
    //private String dose11;
    //private String dose12;

    //private Unit unit;
    //private int interDayFreq;


    public Medicine() {

    }

    public Medicine(String name, String fromDate, String toDate) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getName() {
        return this.name;
    }

    public String getFromDate() {
        return this.fromDate;
    }

    public String getToDate() {
        return this.toDate;
    }
    @NonNull
    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + this.name + '\'' +
                ", fromDate='" + this.fromDate + '\'' +
                ", toDate='" + this.toDate + '\'' +
                '}';
    }
}
