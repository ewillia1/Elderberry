package edu.northeastern.elderberry.dayview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import edu.northeastern.elderberry.R;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    private static final String TAG = "ChildItemAdapter";
    private final List<ChildItem> childItemList;
    private final Context context;
    private Callback callback;
    private int childPosition;

    // Constructor
    ChildItemAdapter(List<ChildItem> childItemList, Context context) {
        Log.d(TAG, "_____ChildItemAdapter");
        this.childItemList = childItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "_____onCreateViewHolder");
        // Here we inflate the corresponding layout of the child item.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item,viewGroup, false);
        return new ChildViewHolder(view, this.context, this.callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {
        Log.d(TAG, "_____onBindViewHolder");
        // Create an instance of the ChildItem class for the given position.
        ChildItem childItem = childItemList.get(position);
        
        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder.childItemTitle.setText(childItem.getChildItemTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        // This method returns the number of items we have added in the ChildItemList
        // i.e. the number of instances of the ChildItemList that have been created
        return childItemList.size();
    }

    // Set the callback.
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    // Callback interface, used to notify when an item's checked status changed.
    // https://gist.github.com/manabreak/301a0c16feaabaee887f32a170b1ebb4
    public interface Callback {
        void onCheckedChanged(CheckBox checkBox, boolean isChecked);
    }

    // This class is to initialize the Views present in the child RecyclerView.
    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ChildViewHolder";
        private final TextView childItemTitle;
        private final Context context;
        private CheckBox checkBox;

        ChildViewHolder(View itemView, Context context, Callback callback) {
            super(itemView);
            Log.d(TAG, "_____ChildViewHolder");
            this.childItemTitle = itemView.findViewById(R.id.child_item_title);
            this.context = context;
            this.checkBox = itemView.findViewById(R.id.checkbox_child_item);
            this.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (callback != null) {
                    callback.onCheckedChanged(this.checkBox, isChecked);
                }
            });
        }
    }
}
