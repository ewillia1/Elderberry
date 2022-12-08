package edu.northeastern.elderberry.dayview;

import android.util.Log;
import android.widget.CheckBox;

public class ChildItem {
    private static final String TAG = "ChildItem";

    // Declaration of the variable
    private String childItemTitle;
    private CheckBox checkBox;

    // Constructor of the class to initialize the variable*
    public ChildItem(String childItemTitle,CheckBox checkBox) {
        Log.d(TAG, "_____ChildItem");
        this.childItemTitle = childItemTitle;
        this.checkBox = checkBox;
    }

    // Getter and Setter method for the parameter
    public String getChildItemTitle() {
        Log.d(TAG, "_____getChildItemTitle");
        return childItemTitle;
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public void setChildItemTitle(String childItemTitle) {
        Log.d(TAG, "_____setChildItemTitle");
        this.childItemTitle = childItemTitle;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
