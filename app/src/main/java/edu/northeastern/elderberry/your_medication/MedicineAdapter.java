package edu.northeastern.elderberry.your_medication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.elderberry.R;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {
    private static final String TAG = "MedicineAdapter";
    private final ArrayList<MedicineRow> medicines;

    public MedicineAdapter(ArrayList<MedicineRow> medicines) {
        Log.d(TAG, "_____StickerAdapter: ");
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "_____onCreateViewHolder: ");
        return new MedicineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        MedicineRow medRow = this.medicines.get(position);
        holder.fromDate.setText(medRow.getFromDate());
        holder.name.setText(medRow.getName());
    }

    @Override
    public int getItemCount() {
        return this.medicines.size();
    }
}
