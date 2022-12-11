package edu.northeastern.elderberry.dayview;

import android.util.Log;

public class ChildItem {
    private static final String TAG = "ChildItem";
    // Declaration of the variable
    private String childItemTitle;
    private boolean takenStatus;

    // Constructor of the class to initialize the variable*
    public ChildItem(String childItemTitle, boolean takenStatus) {
        Log.d(TAG, "_____ChildItem");
        this.childItemTitle = childItemTitle;
        this.takenStatus = takenStatus;
    }

    // Getter and Setter method for the parameter
    public String getChildItemTitle() {
        Log.d(TAG, "_____getChildItemTitle");
        return this.childItemTitle;
    }

    public void setChildItemTitle(String childItemTitle) {
        Log.d(TAG, "_____setChildItemTitle");
        this.childItemTitle = childItemTitle;
    }

    public boolean getTakenStatus() {
        Log.d(TAG, "_____getTakenStatus");
        return this.takenStatus;
    }

    @Override
    public String toString() {
        return "ChildItem{" +
                "childItemTitle='" + childItemTitle + '\'' +
                ", takenStatus=" + takenStatus +
                '}';
    }
}
