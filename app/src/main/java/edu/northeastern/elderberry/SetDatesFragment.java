package edu.northeastern.elderberry;

import static edu.northeastern.elderberry.util.DatetimeFormat.makeDateString;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetDatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO: Make it so the user have to input a from date before the to date
// TODO: Make it so the user to date has to be after the from date
// TODO: Make fields required
public class SetDatesFragment extends Fragment {
    private static final String TAG = "SetDatesFragment";
    private DatePickerDialog from_datePickerDialog;
    private DatePickerDialog to_datePickerDialog;
    private TextView set_from;
    private TextView set_to;

    private boolean fromDateSet;
    private Calendar fromDate;
    private Calendar toDate;

    public SetDatesFragment() {
        Log.d(TAG, "_____SetDatesFragment");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SetDatesFragment.
     */
    public static SetDatesFragment newInstance() {
        Log.d(TAG, "_____newInstance");
        return new SetDatesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "_____onCreateView");
        View view = inflater.inflate(R.layout.fragment_set_dates, container, false);
        this.fromDateSet = false;
        this.set_from = view.findViewById(R.id.fromTextView);
        this.set_to = view.findViewById(R.id.toTextView);
        ImageView from_image = view.findViewById(R.id.from_image);
        from_image.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (from_image)");
            from_datePickerDialog.show();
        });
        ImageView to_image = view.findViewById(R.id.to_image);
        to_image.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (to_image)");
            to_datePickerDialog.show();
        });

        initDatePicker();

        return view;
    }

    private void initDatePicker() {
        Log.d(TAG, "_____initDatePicker");
        // From date.
        DatePickerDialog.OnDateSetListener from_dateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "_____initDatePicker");
            String date = makeDateString(day, month, year);
            this.set_from.setText(date);
            this.set_from.setTextSize(25);
        };

        Calendar calendar = Calendar.getInstance();
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        int style = 0;              // 0 is the default style/theme.
        // Take input from user on date selected from calendar
        this.from_datePickerDialog = new DatePickerDialog(getContext(), style, from_dateSetListener, year1, month1, day1);

        // To date.
        DatePickerDialog.OnDateSetListener to_dateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "_____initDatePicker");
            String date = makeDateString(day, month, year);
            this.set_to.setText(date);
            this.set_to.setTextSize(25);
        };

        this.to_datePickerDialog = new DatePickerDialog(getContext(), style, to_dateSetListener, year1, month1, day1);
    }
}