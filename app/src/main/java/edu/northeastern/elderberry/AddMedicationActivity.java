package edu.northeastern.elderberry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

@SuppressWarnings("unused")
public class AddMedicationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "AddMedicationActivity";
    private static final String TIME_PICKER_TAG = "time picker";
    private DatePickerDialog from_datePickerDialog;
    private DatePickerDialog to_datePickerDialog;
    private TextView select_from;
    private TextView select_to;
    private TextView selectTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_add_medication);

        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        // Providing a subtitle for the ActionBar.
        assert actionBar != null;
        actionBar.setSubtitle(getString(R.string.medication_tracker));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        initDatePicker();

        this.select_from = findViewById(R.id.selectFrom);
        this.select_to = findViewById(R.id.selectTo);

        ImageView time_picker = findViewById(R.id.time_picker);
        time_picker.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), TIME_PICKER_TAG);
        });

        this.selectTime = findViewById(R.id.selectTime);
    }

    private void initDatePicker() {
        Log.d(TAG, "_____initDatePicker");
        // From date.
        DatePickerDialog.OnDateSetListener from_dateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "_____initDatePicker");
            month = month + 1;
            String date = makeDateString(day, month, year);
            this.select_from.setText(getString(R.string.from_date, date));
            this.select_from.setTextSize(25);
        };

        Calendar calendar = Calendar.getInstance();
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        int style = 0;              // 0 is the default style/theme.
        this.from_datePickerDialog = new DatePickerDialog(this, style, from_dateSetListener, year1, month1, day1);

        // To date.
        DatePickerDialog.OnDateSetListener to_dateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "_____initDatePicker");
            month = month + 1;
            String date = makeDateString(day, month, year);
            this.select_to.setText(getString(R.string.to_date, date));
            this.select_to.setTextSize(25);
        };

        this.to_datePickerDialog = new DatePickerDialog(this, style, to_dateSetListener, year1, month1, day1);
    }

    private String makeDateString(int day, int month, int year) {
        Log.d(TAG, "_____makeDateString");
        return getMonthFormat(month) + " " + day + ", " + year;
    }

    private String getMonthFormat(int month) {
        Log.d(TAG, "_____getMonthFormat");
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openFromDatePicker(View view) {
        Log.d(TAG, "_____openDatePicker");
        this.from_datePickerDialog.show();
    }

    public void openToDatePicker(View view) {
        Log.d(TAG, "_____openDatePicker");
        this.to_datePickerDialog.show();
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

        this.selectTime.setText(getString(R.string.set_time, hourOfDay, st_min, am_pm));
    }
}