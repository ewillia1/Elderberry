package edu.northeastern.elderberry.dayview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.elderberry.R;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    private static final String TAG = "ChildItemAdapter";
    private final List<ChildItem> childItemTitle;
    private static SetChildItemClickListener listener;

    // Constructor.
    ChildItemAdapter(List<ChildItem> childItemList, SetChildItemClickListener listener) {
        Log.d(TAG, "_____ChildItemAdapter");
        this.childItemTitle = childItemList;
        ChildItemAdapter.listener = listener;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "_____onCreateViewHolder");
        // Here we inflate the corresponding layout of the child item.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {
        Log.d(TAG, "_____onBindViewHolder");
        // Create an instance of the ChildItem class for the given position.
        ChildItem childItem = childItemTitle.get(position);

        // For the created instance, set title. No need to set the image for the ImageViews because
        // we have provided the source for the images in the layout file itself.
        childViewHolder.childItemTitle.setText(childItem.getChildItemTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        // This method returns the number of items we have added in the ChildItemList.
        // i.e. the number of instances of the ChildItemList that have been created.
        return this.childItemTitle.size();
    }

    // This class is to initialize the Views present in the child RecyclerView.
    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ChildViewHolder";
        private final TextView childItemTitle;
        private final CheckBox takenCheckBox;

        ChildViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "_____ChildViewHolder");
            this.takenCheckBox = itemView.findViewById(R.id.checkbox_child_item);
            this.childItemTitle = itemView.findViewById(R.id.child_item_title);

            // When a checkbox is clicked call the listener's childItemClicked method.
            takenCheckBox.setOnClickListener(v -> listener.childItemClicked(takenCheckBox.isChecked(), getLayoutPosition()));
        }
    }
}
