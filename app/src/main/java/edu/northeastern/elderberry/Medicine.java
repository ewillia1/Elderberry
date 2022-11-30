package edu.northeastern.elderberry;

import android.util.Log;

import androidx.annotation.NonNull;

public class Medicine {
    private static final String TAG = "Medicine";
    private String name;
    private String information;
    private String fromDate;
    private String toDate;
    private String unit;
    private String time1;
    private String time2;
    private String time3;
    private String time4;
    private String time5;
    private String time6;
    private String time7;
    private String time8;
    private String time9;
    private String time10;
    private String time11;
    private String time12;
    private String dose1;
    private String dose2;
    private String dose3;
    private String dose4;
    private String dose5;
    private String dose6;
    private String dose7;
    private String dose8;
    private String dose9;
    private String dose10;
    private String dose11;
    private String dose12;

    public Medicine() {
        Log.d(TAG, "_____Medicine -- zero argument constructor");
    }

    public Medicine(String name, String information, String fromDate, String toDate, String unit) {
        this(name, information, fromDate, toDate, unit, "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "");
        Log.d(TAG, "_____Medicine -- five argument constructor");
    }

    public Medicine(String name, String information, String fromDate, String toDate, String unit, String time1, String dose1) {
        this(name, information, fromDate, toDate, unit, time1, "", "", "", "", "", "", "", "", "", "", "",
                dose1, "", "", "", "", "", "", "", "", "", "", "");
        Log.d(TAG, "_____Medicine -- seven argument constructor");
    }

    public Medicine(String name, String information, String fromDate, String toDate, String unit, String time1, String dose1, String time2, String dose2) {
        this(name, information, fromDate, toDate, unit, time1, time2, "", "", "", "", "", "", "", "", "", "",
                dose1, dose2, "", "", "", "", "", "", "", "", "", "");
        Log.d(TAG, "_____Medicine -- nine argument constructor");
    }
    public Medicine(String name, String information, String fromDate, String toDate, String unit,
                    String time1, String time2, String time3, String time4, String time5, String time6,
                    String time7, String time8, String time9, String time10, String time11, String time12,
                    String dose1, String dose2, String dose3, String dose4, String dose5, String dose6,
                    String dose7, String dose8, String dose9, String dose10, String dose11, String dose12) {
        Log.d(TAG, "_____Medicine -- 29 argument constructor");
        this.name = name;
        this.information = information;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.time6 = time6;
        this.time7 = time7;
        this.time8 = time8;
        this.time9 = time9;
        this.time10 = time10;
        this.time11 = time11;
        this.time12 = time12;
        this.dose1 = dose1;
        this.dose2 = dose2;
        this.dose3 = dose3;
        this.dose4 = dose4;
        this.dose5 = dose5;
        this.dose6 = dose6;
        this.dose7 = dose7;
        this.dose8 = dose8;
        this.dose9 = dose9;
        this.dose10 = dose10;
        this.dose11 = dose11;
        this.dose12 = dose12;
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

    public String getTime1() {
        return this.time1;
    }

    public String getTime2() {
        return this.time2;
    }

    public String getTime3() {
        return this.time3;
    }

    public String getTime4() {
        return this.time4;
    }

    public String getTime5() {
        return this.time5;
    }

    public String getTime6() {
        return this.time6;
    }

    public String getTime7() {
        return this.time7;
    }

    public String getTime8() {
        return this.time8;
    }

    public String getTime9() {
        return this.time9;
    }

    public String getTime10() {
        return this.time10;
    }

    public String getTime11() {
        return this.time11;
    }

    public String getTime12() {
        return this.time12;
    }

    public String getDose1() {
        return this.dose1;
    }

    public String getDose2() {
        return this.dose2;
    }

    public String getDose3() {
        return this.dose3;
    }

    public String getDose4() {
        return this.dose4;
    }

    public String getDose5() {
        return this.dose5;
    }

    public String getDose6() {
        return this.dose6;
    }

    public String getDose7() {
        return this.dose7;
    }

    public String getDose8() {
        return this.dose8;
    }

    public String getDose9() {
        return this.dose9;
    }

    public String getDose10() {
        return this.dose10;
    }

    public String getDose11() {
        return this.dose11;
    }

    public String getDose12() {
        return this.dose12;
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
                ", time1='" + this.time1 + '\'' +
                ", time2='" + this.time2 + '\'' +
                ", time3='" + this.time3 + '\'' +
                ", time4='" + this.time4 + '\'' +
                ", time5='" + this.time5 + '\'' +
                ", time6='" + this.time6 + '\'' +
                ", time7='" + this.time7 + '\'' +
                ", time8='" + this.time8 + '\'' +
                ", time9='" + this.time9 + '\'' +
                ", time10='" + this.time10 + '\'' +
                ", time11='" + this.time11 + '\'' +
                ", time12='" + this.time12 + '\'' +
                ", dose1='" + this.dose1 + '\'' +
                ", dose2='" + this.dose2 + '\'' +
                ", dose3='" + this.dose3 + '\'' +
                ", dose4='" + this.dose4 + '\'' +
                ", dose5='" + this.dose5 + '\'' +
                ", dose6='" + this.dose6 + '\'' +
                ", dose7='" + this.dose7 + '\'' +
                ", dose8='" + this.dose8 + '\'' +
                ", dose9='" + this.dose9 + '\'' +
                ", dose10='" + this.dose10 + '\'' +
                ", dose11='" + this.dose11 + '\'' +
                ", dose12='" + this.dose12 + '\'' +
                '}';
    }
}
