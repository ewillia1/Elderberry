package edu.northeastern.elderberry;

import android.widget.CheckBox;

public interface ParentItemClickListener {
    void onChildItemClick(int parentPosition, int childPosition, CheckBox cb);
}
