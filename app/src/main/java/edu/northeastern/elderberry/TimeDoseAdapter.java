package edu.northeastern.elderberry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// TODO: Finish
public class TimeDoseAdapter extends RecyclerView.Adapter<TimeDoseViewHolder> {
    private static final String TAG = "LinkCollectorAdapter";
    private final ArrayList<TimeDoseItem> timeDoseItemArrayList;
    private final Context context;
    private final OnTimeDoseItemListener onLinkItemListener;

    public TimeDoseAdapter(ArrayList<TimeDoseItem> timeDoseItem, Context context, OnTimeDoseItemListener onLinkItemListener) {
        Log.d(TAG, "_____constructor");
        this.timeDoseItemArrayList = timeDoseItem;
        this.context = context;
        this.onLinkItemListener = onLinkItemListener;
    }

    @NonNull
    @Override
    public TimeDoseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "_____onCreateViewHolder");
        // Create an instance of the view holder by passing it the layout inflated as view and no root.
        return new TimeDoseViewHolder(LayoutInflater.from(this.context).inflate(R.layout.card_view, parent, false), this.onLinkItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeDoseViewHolder holder, int position) {
        Log.d(TAG, "_____onBindViewHolder");
        holder.bindThisData(this.timeDoseItemArrayList.get(position));
        holder.time.setText(this.timeDoseItemArrayList.get(position).getTime());
        holder.dose.setText(this.timeDoseItemArrayList.get(position).getDose());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        // Returns the size of the recyclerview that is the list of the arraylist.
        return this.timeDoseItemArrayList.size();
    }
}