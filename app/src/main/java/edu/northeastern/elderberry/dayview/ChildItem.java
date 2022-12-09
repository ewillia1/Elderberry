package edu.northeastern.elderberry.dayview;

import android.util.Log;

public class ChildItem {
    private static final String TAG = "ChildItem";
    // Declaration of the variable
    private String childItemTitle;

    // Constructor of the class to initialize the variable*
    public ChildItem(String childItemTitle) {
        Log.d(TAG, "_____ChildItem");
        this.childItemTitle = childItemTitle;
    }

    // Getter and Setter method for the parameter
    public String getChildItemTitle() {
        Log.d(TAG, "_____getChildItemTitle");
        return this.childItemTitle;
    }

//    public boolean isTaken() {
//        return this.taken;
//    }
//
//    public void setTaken(boolean taken) {
//        this.taken = taken;
//    }

    public void setChildItemTitle(String childItemTitle) {
        Log.d(TAG, "_____setChildItemTitle");
        this.childItemTitle = childItemTitle;
    }
}
