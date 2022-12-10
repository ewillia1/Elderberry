package edu.northeastern.elderberry.your_medication;

import static edu.northeastern.elderberry.util.DatetimeFormat.makeStringDate;

import android.util.Log;

import java.util.Calendar;
import java.util.Comparator;

public class SortMedicineRow implements Comparator<MedicineRow> {
    private static final String TAG = "SortMedicineRow";

    @Override
    public int compare(MedicineRow o1, MedicineRow o2) {
        Log.d(TAG, "_____compare");
        String o1FromDateSt = o1.getFromDate();         // Ex. DEC 1, 2022
        String o2FromDateSt = o2.getFromDate();         // Ex. DEC 10, 2022
        Calendar o1FromDate = makeStringDate(o1FromDateSt);
        Calendar o2FromDate = makeStringDate(o2FromDateSt);
        return o1FromDate.compareTo(o2FromDate);
    }
}
