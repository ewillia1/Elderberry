package edu.northeastern.elderberry.addMed;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.elderberry.R;

// TODO: Finish
public class TimeDoseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private final String TAG = "TimeDoseViewHolder";
    private static final String TIME_PICKER_TAG = "time_picker";
    public final TextView itemNumber;
    public final TextView timeTextView;
    public final EditText doseTextView;
    public final TextView unitTextView;
    public final OnTimeDoseItemListener onTimeDoseItemListener;

    public TimeDoseViewHolder(@NonNull View itemView, OnTimeDoseItemListener onTimeDoseItemListener, Context context) {
        super(itemView);
        Log.d(TAG, "_____constructor");
        this.itemNumber = itemView.findViewById(R.id.itemNumber);
        this.timeTextView = itemView.findViewById(R.id.time_recyclerView);
        this.doseTextView = itemView.findViewById(R.id.dose_recyclerView);
        this.unitTextView = itemView.findViewById(R.id.unit_recyclerView);
        this.onTimeDoseItemListener = onTimeDoseItemListener;

        itemView.setOnClickListener(this);

        itemView.setOnClickListener(view -> {
            int position = getAdapterPosition();
            Log.d(TAG, "_____itemView.setOnClickListener: item clicked " + (position + 1));
            onTimeDoseItemListener.onTimeDoseItemClick(position);
        });

        this.timeTextView.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (this.time)");
            DialogFragment timePicker = new TimePickerFragment();

            // TODO: Get this to work.
//            timePicker.show(((Activity) this.context).getFragmentManager(), TIME_PICKER_TAG);
        });

        // TODO: Make it so the decimal works!
        this.doseTextView.setOnClickListener(v -> Log.d(TAG, "_____onClick (this.dose)"));

        this.unitTextView.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (this.unit)");
            PopupMenu popupMenu = new PopupMenu(unitTextView.getContext(), itemView);
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.tabs) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.tabs)");
                    this.unitTextView.setText(R.string.tabs);
                    return true;
                } else if (itemId == R.id.pills) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.pills)");
                    this.unitTextView.setText(R.string.pills);
                    return true;
                } else if (itemId == R.id.grams) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.grams)");
                    this.unitTextView.setText(R.string.grams);
                    return true;
                } else if (itemId == R.id.puffs) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.puffs)");
                    this.unitTextView.setText(R.string.puffs);
                    return true;
                } else if (itemId == R.id.teaspoons) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.teaspoons)");
                    this.unitTextView.setText(R.string.teaspoons);
                    return true;
                } else if (itemId == R.id.tablespoons) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.tablespoons)");
                    this.unitTextView.setText(R.string.tablespoons);
                    return true;
                } else if (itemId == R.id.mL) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.mL)");
                    this.unitTextView.setText(R.string.ml);
                    return true;
                } else if (itemId == R.id.cc) {
                    Log.d(TAG, "_____onMenuItemClick (R.id.cc)");
                    this.unitTextView.setText(R.string.cc);
                    return true;
                }
                Log.d(TAG, "_____onMenuItemClick (default)");
                this.unitTextView.setText(R.string.click_to_set_unit);
                return false;
            });
            popupMenu.inflate(R.menu.unit);
            popupMenu.setGravity(Gravity.END);
            popupMenu.show();
        });
    }

    public void bindThisData(TimeDoseItem theLinkToBind) {
        Log.d(TAG, "_____bindThisData");
        this.timeTextView.setText(theLinkToBind.getTime());
        this.doseTextView.setText(theLinkToBind.getDose());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "_____onClick");
        this.onTimeDoseItemListener.onTimeDoseItemClick(getAdapterPosition());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "_____onTimeSet");
        String am_pm = (hourOfDay < 12) ? " AM" : " PM";
        String st_min = Integer.toString(minute);

        if (hourOfDay > 12) {
            hourOfDay %= 12;
        }

        if (minute < 10) {
            st_min = "0" + st_min;
        }

        this.timeTextView.setText(itemView.getContext().getString(R.string.set_time, hourOfDay, st_min, am_pm));
    }
}