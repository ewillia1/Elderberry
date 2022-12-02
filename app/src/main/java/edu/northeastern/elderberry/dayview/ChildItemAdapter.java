package edu.northeastern.elderberry.dayview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.northeastern.elderberry.R;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    private static final String TAG = "ChildItemAdapter";
    private final List<ChildItem> ChildItemList;

    // Constructor
    ChildItemAdapter(List<ChildItem> childItemList) {
        Log.d(TAG, "_____ChildItemAdapter");
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "_____onCreateViewHolder");
        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item,viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder,int position) {
        Log.d(TAG, "_____onBindViewHolder");
        // Create an instance of the ChildItem class for the given position.
        ChildItem childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder.ChildItemTitle.setText(childItem.getChildItemTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        // This method returns the number of items we have added in the ChildItemList
        // i.e. the number of instances of the ChildItemList that have been created
        return ChildItemList.size();
    }

    // This class is to initialize the Views present in the child RecyclerView.
    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ChildViewHolder";
        final TextView ChildItemTitle;

        ChildViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "_____ChildViewHolder");
            ChildItemTitle = itemView.findViewById(R.id.child_item_title);
        }
    }
}
