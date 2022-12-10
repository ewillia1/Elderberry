package edu.northeastern.elderberry.dayview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.elderberry.OnListItemClick;
import edu.northeastern.elderberry.ParentItemClickListener;
import edu.northeastern.elderberry.R;

/**
 * Accommodate long medicine names
 */
public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {
    private static final String TAG = "ParentItemAdapter";
    // An object of RecyclerView.RecycledViewPool is created to share the Views between the child and the parent RecyclerViews.
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<ParentItem> itemList;
    private final Context context;
    ParentItemClickListener rvClickListener;
    OnListItemClick childListener;
    private int childPos;
    private int parentPos;
    private CheckBox takenCheckBox;

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

        return new ParentViewHolder(view, this.rvClickListener, this.childPos, this.takenCheckBox);
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
        parentViewHolder.childRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.childRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.childRecyclerView.setRecycledViewPool(viewPool);

        // Todo enable this onClickListener
        OnListItemClick onListItemClick = new OnListItemClick() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onClick(int childPosition, CheckBox cb) {
                childPos = childPosition;
                takenCheckBox = cb;
                rvClickListener.onChildItemClick(parentViewHolder.getAbsoluteAdapterPosition(), childPosition, cb);
                //Log.d(TAG, "_____onChildItemClick: parentPos is " + position);
                //Log.d(TAG, "_____onChildItemClick: childPosition is " + childPosition);
                // this.childPos = childPosition;
            }

            @Override
            public int getPos() {
                return childPos;
            }
        };

        this.childListener = onListItemClick;
        this.childPos = onListItemClick.getPos();
        childItemAdapter.setClickListener(onListItemClick);
    }

    public void setParentItemClickListener(ParentItemClickListener rvClickListener) {
        Log.d(TAG, "_____setRvItemClickListener");
        this.rvClickListener = rvClickListener;
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
        private final CheckBox takenCheckBox;
        private final RecyclerView childRecyclerView;

        ParentViewHolder(final View itemView, final ParentItemClickListener parentListener, int childPos, CheckBox cb) {
            super(itemView);
            Log.d(TAG, "_____ParentViewHolder");
            this.parentItemTitle = itemView.findViewById(R.id.parent_item_title);
            this.childRecyclerView = itemView.findViewById(R.id.child_recyclerview);
            this.takenCheckBox = cb;


            itemView.setOnClickListener(v -> {
                Log.d(TAG, "_____MedicineHolder: ");
                if (parentListener != null) {
                    int parentPos = getLayoutPosition();

                    if (parentPos != RecyclerView.NO_POSITION) {
                        parentPos = getLayoutPosition();
                        parentListener.onChildItemClick(parentPos, childPos, this.takenCheckBox);
                    }
                }
            });
        }

    }
}

