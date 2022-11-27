package edu.northeastern.elderberry.your_medication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.elderberry.OnListItemClick;
import edu.northeastern.elderberry.R;

public class MedicineHolder extends RecyclerView.ViewHolder {
    public static final String TAG = "MedicineHolder";
    public final TextView name;
    public final TextView fromDate;

    public MedicineHolder(@NonNull View itemView, final OnListItemClick listener) {
        super(itemView);
        Log.d(TAG, "_____MedicineHolder: ");
        this.name = itemView.findViewById(R.id.yourMedNameTextView);
        this.fromDate = itemView.findViewById(R.id.yourMedTimeTextView);

        itemView.setOnClickListener(v -> {
            Log.d(TAG, "_____MedicineHolder: ");
            if (listener!=null) {
                int position = getLayoutPosition();

                if (position!= RecyclerView.NO_POSITION) {
                    listener.onClick(position);
                }
            }
        });
    }
}
