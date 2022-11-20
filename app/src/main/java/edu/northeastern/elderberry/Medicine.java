package edu.northeastern.elderberry;

import java.util.Date;

import edu.northeastern.elderberry.enums.Unit;

public class Medicine {
    private String medName;
    private String dose;
    private String fromDate;
    private String toDate;
    private Unit unit;
    private int interDayFreq;

    public Medicine(String medName, String dose, String fromDate, String toDate, Unit unit, int interDayFreq) {
        this.medName = medName;
        this.dose = dose;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.unit = unit;
        this.interDayFreq = interDayFreq;
    }

    public String getMedName() {
        return medName;
    }

    public String getDose() {
        return dose;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public Unit getUnit() {
        return unit;
    }

    public int getInterDayFreq() {
        return interDayFreq;
    }
}
