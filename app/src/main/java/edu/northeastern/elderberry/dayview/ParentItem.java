package edu.northeastern.elderberry.dayview;

import android.util.Log;

import java.util.List;

public class ParentItem {
    private static final String TAG = "ParentItem";
    private String parentItemTitle;
    private List<ChildItem> childItemList;

    // Constructor of the class to initialize the variables.
    public ParentItem(String ParentItemTitle, List<ChildItem> ChildItemList) {
        Log.d(TAG, "_____ParentItem");
        this.parentItemTitle = ParentItemTitle;
        this.childItemList = ChildItemList;
    }

    // Getter and Setter methods for each parameter.
    public String getParentItemTitle() {
        Log.d(TAG, "_____getParentItemTitle");
        return this.parentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle) {
        Log.d(TAG, "_____setParentItemTitle");
        this.parentItemTitle = parentItemTitle;
    }

    public List<ChildItem> getChildItemList() {
        Log.d(TAG, "_____getChildItemList");
        return this.childItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        Log.d(TAG, "_____setChildItemList");
        this.childItemList = childItemList;
    }
}
