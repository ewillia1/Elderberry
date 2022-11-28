package edu.northeastern.elderberry.your_medication;

import edu.northeastern.elderberry.Medicine;

public class MedicineRow {
    private final String name;
    private final String fromDate;
    private final String toDate;

    public MedicineRow(String name, String fromDate, String toDate) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public static MedicineRow build(Medicine md) {
        return new MedicineRow(md.getName(), md.getFromDate(), md.getToDate());
    }
}
