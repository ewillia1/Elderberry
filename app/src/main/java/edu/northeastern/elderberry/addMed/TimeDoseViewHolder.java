package edu.northeastern.elderberry.addMed;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import edu.northeastern.elderberry.R;

public class TimeDoseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final String TAG = "TimeDoseViewHolder";
    public final TextView itemNumber;
    public final TextView timeTextView;
    public final EditText doseEditText;
    public final OnTimeDoseItemListener onTimeDoseItemListener;
    private TimePickerDialog timePickerDialog;
    private final Context context;
    private String time;
    private String dose;

    public TimeDoseViewHolder(@NonNull View itemView, OnTimeDoseItemListener onTimeDoseItemListener, Context context) {
        super(itemView);
        Log.d(TAG, "_____constructor");
        this.itemNumber = itemView.findViewById(R.id.itemNumber);
        this.timeTextView = itemView.findViewById(R.id.time_recyclerView);
        this.doseEditText = itemView.findViewById(R.id.dose_recyclerView);
        this.onTimeDoseItemListener = onTimeDoseItemListener;
        this.context = context;

        itemView.setOnClickListener(this);

        // This onClickListener does not really add functionality to our app.
        itemView.setOnClickListener(view -> {
            int position = getAbsoluteAdapterPosition();
            Log.d(TAG, "_____itemView.setOnClickListener: item clicked " + (position + 1));
            this.onTimeDoseItemListener.onTimeDoseItemClick(position);
        });

        this.timeTextView.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (this.time)");
            this.timePickerDialog.show();
        });

        initTimePicker();

        this.doseEditText.setOnFocusChangeListener((v, hasFocus) -> {
            int position = getAbsoluteAdapterPosition();
            this.dose = this.doseEditText.getText().toString();
            Log.d(TAG, "_____onFocusChange");
            this.onTimeDoseItemListener.doseWasAdded(position, this.dose);
        });

        this.doseEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                int position = getAbsoluteAdapterPosition();
                this.dose = this.doseEditText.getText().toString();
                Log.d(TAG, "_____onEditorAction");
                this.onTimeDoseItemListener.doseWasAdded(position, this.dose);
            }
            return false;
        });


    }

    private void initTimePicker() {
        Log.d(TAG, "_____initTimePicker");
        // Time.
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
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
            this.time = itemView.getContext().getString(R.string.set_time, hourOfDay, st_min, am_pm);

            int position = getAbsoluteAdapterPosition();
            this.onTimeDoseItemListener.timeWasAdded(position, this.time);
        };

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        // Take input from user on time selected from calendar.
        this.timePickerDialog = new TimePickerDialog(this.context, timeSetListener, hourOfDay, minute, false);
    }

    public void bindThisData(TimeDoseItem theLinkToBind) {
        Log.d(TAG, "_____bindThisData");
        this.timeTextView.setText(theLinkToBind.getTime());
        this.doseEditText.setText(theLinkToBind.getDose());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "_____onClick");
        this.onTimeDoseItemListener.onTimeDoseItemClick(getAbsoluteAdapterPosition());
    }
}