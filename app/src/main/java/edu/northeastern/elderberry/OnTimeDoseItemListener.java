package edu.northeastern.elderberry;

/**
 * Interface used in RecyclerView OnClickListener.
 */
public interface OnTimeDoseItemListener {
    /**
     * Method to Override in the LinkCollectorActivity class.
     *
     * @param position (int)
     */
    void onLinkItemClick(int position);
}