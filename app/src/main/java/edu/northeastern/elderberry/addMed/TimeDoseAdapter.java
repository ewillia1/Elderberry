package edu.northeastern.elderberry.addMed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import edu.northeastern.elderberry.R;

// TODO: Finish
public class TimeDoseAdapter extends RecyclerView.Adapter<TimeDoseViewHolder> {
    private static final String TAG = "TimeDoseAdapter";
    private final ArrayList<TimeDoseItem> timeDoseItemArrayList;
    private final Context context;
    private final OnTimeDoseItemListener onTimeDoseItemListener;

    public TimeDoseAdapter(ArrayList<TimeDoseItem> timeDoseItem, Context context, OnTimeDoseItemListener onTimeDoseItemListener) {
        Log.d(TAG, "_____constructor");
        this.timeDoseItemArrayList = timeDoseItem;
        this.context = context;
        this.onTimeDoseItemListener = onTimeDoseItemListener;
    }

    @NonNull
    @Override
    public TimeDoseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "_____onCreateViewHolder");
        // Create an instance of the view holder by passing it the layout inflated as view and no root.
        return new TimeDoseViewHolder(LayoutInflater.from(this.context).inflate(R.layout.card_view, parent, false), this.onTimeDoseItemListener, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeDoseViewHolder holder, int position) {
        Log.d(TAG, "_____onBindViewHolder");
        holder.bindThisData(this.timeDoseItemArrayList.get(position));
        this.timeDoseItemArrayList.get(position).setItemNumber(position + 1);
        holder.itemNumber.setText(String.format(Locale.getDefault(),"%d", this.timeDoseItemArrayList.get(position).getItemNumber()));
        holder.timeTextView.setText(this.timeDoseItemArrayList.get(position).getTime());
        holder.doseTextView.setText(this.timeDoseItemArrayList.get(position).getDose());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        // Returns the size of the recyclerview that is the list of the arraylist.
        return this.timeDoseItemArrayList.size();
    }
}