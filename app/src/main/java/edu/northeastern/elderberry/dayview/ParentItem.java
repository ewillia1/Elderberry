package edu.northeastern.elderberry.dayview;

import android.util.Log;

import java.util.List;

public class ParentItem {
    private static final String TAG = "ParentItem";
    // Declaration of the variables
    private String ParentItemTitle;
    private List<ChildItem> ChildItemList;


    // Constructor of the class to initialize the variables
    public ParentItem(String ParentItemTitle, List<ChildItem> ChildItemList) {
        Log.d(TAG, "_____ParentItem");
        this.ParentItemTitle = ParentItemTitle;
        this.ChildItemList = ChildItemList;
    }


    // Getter and Setter methods for each parameter
    public String getParentItemTitle() {
        Log.d(TAG, "_____getParentItemTitle");
        return ParentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle) {
        Log.d(TAG, "_____setParentItemTitle");
        ParentItemTitle = parentItemTitle;
    }

    public List<ChildItem> getChildItemList() {
        Log.d(TAG, "_____getChildItemList");
        return ChildItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        Log.d(TAG, "_____setChildItemList");
        ChildItemList = childItemList;
    }
}
