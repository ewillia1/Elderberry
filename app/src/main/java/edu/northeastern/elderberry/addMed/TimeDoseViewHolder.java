package edu.northeastern.elderberry.addMed;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import edu.northeastern.elderberry.R;

public class TimeDoseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView itemNumber;
    public final TextView timeTextView;
    public final EditText doseEditText;
    public final OnTimeDoseItemListener onTimeDoseItemListener;
    private final String TAG = "TimeDoseViewHolder";
    private final int MAX_HOUR = 12;
    private final int TEN_MIN = 10;
    private final Context context;
    private TimePickerDialog timePickerDialog;
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

        // So when the user clicks enter while in the last card (time/dose item) in the recycler view it goes to the add button.
        itemView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.d(TAG, "_____onKey");
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
            }
            return false;
        });

        this.timeTextView.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (this.time)");
            this.timePickerDialog.show();
        });

        initTimePicker();

        // Every time a new character is added to the TextInputEditText for dose, the viewModel is updated.
        this.doseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "_____addTextChangedListener (beforeTextChanged): s = " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "_____addTextChangedListener (onTextChanged): s = " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int position = getAbsoluteAdapterPosition();
                    dose = doseEditText.getText().toString();
                    Log.d(TAG, "_____addTextChangedListener (afterTextChanged): position = " + position + ", s = " + s);
                    onTimeDoseItemListener.doseWasAdded(position, dose);
                }
            }
        });
    }

    private void initTimePicker() {
        Log.d(TAG, "_____initTimePicker");
        // Time.
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            Log.d(TAG, "_____onTimeSet");
            String am_pm = (hourOfDay < MAX_HOUR) ? "AM" : "PM";
            String st_min = Integer.toString(minute);

            if (hourOfDay > MAX_HOUR) {
                hourOfDay %= MAX_HOUR;
            }

            if (hourOfDay == 0) {
                hourOfDay = 12;
            }

            if (minute < TEN_MIN) {
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