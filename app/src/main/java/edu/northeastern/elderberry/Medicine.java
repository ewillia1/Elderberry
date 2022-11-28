package edu.northeastern.elderberry;

import androidx.annotation.NonNull;

public class Medicine {
    private String name;
    //private String dose;
    private String fromDate;
    private String toDate;
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

//    public String getFromDate() {
//        return this.fromDate;
//    }
//
//    public String getToDate() {
//        return this.toDate;
//    }

    @NonNull
    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
