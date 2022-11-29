package edu.northeastern.elderberry.addMed;

/**
 * Interface used in RecyclerView OnClickListener.
 */
public interface OnTimeDoseItemListener {
    void onTimeDoseItemClick(int position);

    void timeWasAdded(String time);

    void doseWasAdded(String dose);
}