package edu.northeastern.elderberry;

import android.widget.CheckBox;

public interface OnListItemClick {
    // position is passed as an argument
    void onClick(int position);
    void onClick(int position, CheckBox cb);

    public int getPos();
}
