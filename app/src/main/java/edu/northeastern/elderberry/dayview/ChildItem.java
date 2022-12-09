package edu.northeastern.elderberry.dayview;

import android.util.Log;

public class ChildItem {
    private static final String TAG = "ChildItem";

    // Declaration of the variable
    private String ChildItemTitle;

    // Constructor of the class to initialize the variable*
    public ChildItem(String childItemTitle) {
        Log.d(TAG, "_____ChildItem");
        this.ChildItemTitle = childItemTitle;
    }

    // Getter and Setter method for the parameter
    public String getChildItemTitle() {
        Log.d(TAG, "_____getChildItemTitle");
        return ChildItemTitle;
    }

    public void setChildItemTitle(String childItemTitle) {
        Log.d(TAG, "_____setChildItemTitle");
        ChildItemTitle = childItemTitle;
    }
}
