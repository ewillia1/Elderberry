package edu.northeastern.elderberry.your_medication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.elderberry.OnListItemClick;
import edu.northeastern.elderberry.R;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {
    private static final String TAG = "MedicineAdapter";
    private final ArrayList<MedicineRow> medicines;
    private OnListItemClick listener;

    public MedicineAdapter(ArrayList<MedicineRow> medicines) {
        Log.d(TAG, "_____StickerAdapter: ");
        this.medicines = medicines;
    }

    public void setClickListener(OnListItemClick listener) {
        Log.d(TAG, "_____setClickListener: ");
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "_____onCreateViewHolder: ");
        return new MedicineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_row, parent, false), this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        MedicineRow medRow = this.medicines.get(position);
        holder.fromDate.setText(String.valueOf(medRow.getFromDate()));
        holder.name.setText(String.valueOf(medRow.getName()));
    }

    @Override
    public int getItemCount() {
        return this.medicines.size();
    }
}
