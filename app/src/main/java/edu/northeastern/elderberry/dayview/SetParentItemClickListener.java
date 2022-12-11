package edu.northeastern.elderberry.dayview;

import android.widget.CheckBox;

public interface SetParentItemClickListener {
    void parentItemClicked(int parentPosition, int childPosition, boolean isChecked);
}