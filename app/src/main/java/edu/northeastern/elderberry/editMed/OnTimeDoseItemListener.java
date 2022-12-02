package edu.northeastern.elderberry.editMed;

/**
 * Interface used in RecyclerView OnClickListener.
 */
public interface OnTimeDoseItemListener {
    void onTimeDoseItemClick(int position);

    void timeWasAdded(int index, String time);

    void doseWasAdded(int index, String dose);
}