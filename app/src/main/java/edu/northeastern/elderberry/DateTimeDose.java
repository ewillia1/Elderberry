package edu.northeastern.elderberry;

import java.util.Date;

public class DateTimeDose {
    private Date fromTime;
    private Date toDate;
    private int dose;
    private String name;

    public DateTimeDose(Date fromTime, Date toDate, int dose, String name) {
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.dose = dose;
        this.name = name;
    }

    public DateTimeDose() {
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
