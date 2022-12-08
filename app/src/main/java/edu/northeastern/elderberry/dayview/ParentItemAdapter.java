package edu.northeastern.elderberry.dayview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.elderberry.R;

public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {
    private static final String TAG = "ParentItemAdapter";
    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews.
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<ParentItem> itemList;
    private final Context context;

    ParentItemAdapter(List<ParentItem> itemList, Context context) {
        Log.d(TAG, "_____ParentItemAdapter");
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "_____onCreateViewHolder");
        // Here we inflate the corresponding layout of the parent item.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_item, viewGroup, false);

        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position) {
        Log.d(TAG, "_____onBindViewHolder");
        // Create an instance of the ParentItem class for the given position.
        ParentItem parentItem = itemList.get(position);

        // For the created instance, get the title and set it as the text for the TextView.
        parentViewHolder.parentItemTitle.setText(parentItem.getParentItemTitle());

        // Create a layout manager to assign a layout to the RecyclerView.

        // Here we have assigned the layout as LinearLayout with vertical orientation.
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.childRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        // Since this is a nested layout, so to define how many child items should be prefetched when the
        // child RecyclerView is nested inside the parent RecyclerView, we use the following method.
        layoutManager.setInitialPrefetchItemCount(parentItem.getChildItemList().size());

        // Create an instance of the child item view adapter and set its adapter, layout manager and RecyclerViewPool.
        ChildItemAdapter childItemAdapter = new ChildItemAdapter(parentItem.getChildItemList(), this.context);
        childItemAdapter.setCallback(this::onCheckboxClicked);
        parentViewHolder.childRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.childRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.childRecyclerView.setRecycledViewPool(viewPool);
    }

    private void onCheckboxClicked(CheckBox checkBox, boolean isChecked) {
        Log.d(TAG, "_____onCheckboxClicked: isChecked = " + isChecked);
        Log.d(TAG, "_____onCheckboxClicked: checkboxID = checkbox");

        if (isChecked) {
            setChecked(checkBox, "checkboxstate");
        } else {
            setUnchecked(checkBox, "checkboxstate");
        }
    }

    private void setChecked(CheckBox checkBox, String checkBoxString) {
        Log.d(TAG, "_____setComplete");
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean(checkBoxString, true).commit();
        checkBox.setChecked(checked);
        // fromDate 1 Dec, 2022
        // to Date 31 Dec, 222
        // total of 31 days inclusive of both end
        // freq = 3
        // size of the array 31 * 3 = 93

        // pick 7 Dec, 2022, 2nd time you are taking the med
        // 0 index: retrieving the correct medicine
        // e.g. 1
        // 1st index is based off date
        /// 2nd index is based off position
        // 6 * 3 + 1 = 19

        // e.g. 2. 1 dec 2022, 1 st first frequency
        // 0 * 3 + 0 = 0
        // [false, false, false,  ... ,false] of size 93
        // 2 Todo the ability to check and uncheck the boolean value in the database
    }

    private void setUnchecked(CheckBox checkBox, String checkBoxString) {
        Log.d(TAG, "_____setIncomplete");
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean(checkBoxString, false).commit();
        checkBox.setChecked(checked);
    }

    // This method returns the number of items we have added in the ParentItemList i.e. the number
    // of instances we have created of the ParentItemList.
    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        return itemList.size();
    }

    // This class is to initialize the Views present in the parent RecyclerView.
    static class ParentViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ParentViewHolder";
        private final TextView parentItemTitle;
        private final RecyclerView childRecyclerView;

        ParentViewHolder(final View itemView) {
            super(itemView);
            Log.d(TAG, "_____ParentViewHolder");
            this.parentItemTitle = itemView.findViewById(R.id.parent_item_title);
            this.childRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}

