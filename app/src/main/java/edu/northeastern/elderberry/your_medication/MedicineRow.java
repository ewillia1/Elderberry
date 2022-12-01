package edu.northeastern.elderberry.your_medication;

import android.util.Log;

import edu.northeastern.elderberry.Medicine;

public class MedicineRow {
    private final static String TAG = "MedicineRow";
    private final String name;
    private final String fromDate;
    private final String toDate;

    public MedicineRow(String name, String fromDate, String toDate) {
        Log.d(TAG, "_____MedicineRow");
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getName() {
        Log.d(TAG, "_____getName");
        return name;
    }

    public String getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return fromDate;
    }

    public String getToDate() {
        Log.d(TAG, "_____getToDate");
        return toDate;
    }

    public static MedicineRow build(Medicine md) {
        Log.d(TAG, "_____build");
        return new MedicineRow(md.getName(), md.getFromDate(), md.getToDate());
    }
}
