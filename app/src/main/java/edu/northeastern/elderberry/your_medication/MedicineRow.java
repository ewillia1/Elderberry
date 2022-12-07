package edu.northeastern.elderberry.your_medication;

import android.util.Log;

import edu.northeastern.elderberry.Medicine;

public class MedicineRow {
    private final static String TAG = "MedicineRow";
    private final String id;
    private final String name;
    private final String fromDate;

    public MedicineRow(String id, String name, String fromDate, String toDate) {
        Log.d(TAG, "_____MedicineRow");
        this.id = id;
        this.name = name;
        this.fromDate = fromDate;
    }

    public String getId() {
        Log.d(TAG, "_____getId");
        return this.id;
    }

    public String getName() {
        Log.d(TAG, "_____getName");
        return this.name;
    }

    public String getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return this.fromDate;
    }

    public static MedicineRow build(Medicine md) {
        Log.d(TAG, "_____build");
        return new MedicineRow(md.getId(), md.getName(), md.getFromDate(), md.getToDate());
    }
}
