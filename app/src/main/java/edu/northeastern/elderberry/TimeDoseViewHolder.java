package edu.northeastern.elderberry;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// TODO: Finish
public class TimeDoseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView time;
    public final TextView dose;
    public final OnTimeDoseItemListener onLinkItemListener;
    private final String TAG = "LinkViewHolder";

    public TimeDoseViewHolder(@NonNull View itemView, OnTimeDoseItemListener onLinkItemListener) {
        super(itemView);
        Log.d(TAG, "_____constructor");
        this.time = itemView.findViewById(R.id.time);
        this.dose = itemView.findViewById(R.id.dose);
        this.onLinkItemListener = onLinkItemListener;

        itemView.setOnClickListener(this);

        itemView.setOnClickListener(view -> {
            Log.d(TAG, "_____itemView.setOnClickListener");
            int position = getAdapterPosition();
            // Get the website to open up based on the item link.
            onLinkItemListener.onLinkItemClick(position);
        });
    }

    public void bindThisData(TimeDoseItem theLinkToBind) {
        Log.d(TAG, "_____bindThisData");
        this.time.setText(theLinkToBind.getTime());
        this.dose.setText(theLinkToBind.getDose());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "_____onClick");
        onLinkItemListener.onLinkItemClick(getAdapterPosition());
    }
}