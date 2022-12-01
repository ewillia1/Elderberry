package edu.northeastern.elderberry;

import android.util.Log;

public class Dose {
    private static final String TAG = "Dose";
    private String dose;

    public Dose(String dose) {
        Log.d(TAG, "_____Dose");
        this.dose = dose;
    }

    public String getDose() {
        Log.d(TAG, "_____getDose");
        return this.dose;
    }

    public void setDose(String dose) {
        Log.d(TAG, "_____getDose");
        this.dose = dose;
    }
}
