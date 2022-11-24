package edu.northeastern.elderberry.addMed;

import static edu.northeastern.elderberry.util.DatetimeFormat.makeDateString;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

import edu.northeastern.elderberry.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetDatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO: Make it so the user have to input a from date before the to date
// TODO: Make it so the user to date has to be after the from date
// TODO: Make fields required
// TODO: Get landscape layout to look good/work as expected.
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
        this.fromDateSet = false;
        this.fromDate = new Calendar() {
            @Override
            protected void computeTime() {
                Log.d(TAG, "_____computeTime -- this.fromDate");
            }

            @Override
            protected void computeFields() {
                Log.d(TAG, "_____computeFields -- this.fromDate");
            }

            @Override
            public void add(int field, int amount) {
                Log.d(TAG, "_____add -- this.fromDate");
            }

            @Override
            public void roll(int field, boolean up) {
                Log.d(TAG, "_____roll -- this.fromDate");
            }

            @Override
            public int getMinimum(int field) {
                Log.d(TAG, "_____getMinimum -- this.fromDate");
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                Log.d(TAG, "_____getMaximum -- this.fromDate");
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                Log.d(TAG, "_____getGreatestMinimum -- this.fromDate");
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                Log.d(TAG, "_____getLeastMaximum -- this.fromDate");
                return 0;
            }
        };

        this.toDate = new Calendar() {
            @Override
            protected void computeTime() {
                Log.d(TAG, "_____computeTime -- this.toDate");
            }

            @Override
            protected void computeFields() {
                Log.d(TAG, "_____computeFields -- this.toDate");
            }

            @Override
            public void add(int field, int amount) {
                Log.d(TAG, "_____add -- this.toDate");
            }

            @Override
            public void roll(int field, boolean up) {
                Log.d(TAG, "_____roll -- this.toDate");
            }

            @Override
            public int getMinimum(int field) {
                Log.d(TAG, "_____getMinimum -- this.toDate");
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                Log.d(TAG, "_____getMaximum -- this.toDate");
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                Log.d(TAG, "_____getGreatestMinimum -- this.toDate");
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                Log.d(TAG, "_____getLeastMaximum -- this.toDate");
                return 0;
            }
        };
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
            this.fromDateSet = true;
            this.set_from.setText(date);
            this.set_from.setTextSize(25);
            Log.d(TAG, "_____initDatePicker fromDate -- this.fromDate = " + this.fromDate);
            this.fromDate.set(year, month, day);
            Log.d(TAG, "_____initDatePicker fromDate -- this.fromDate = " + this.fromDate);
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
//            String date = makeDateString(day, month, year);
//            this.set_to.setText(date);
//            this.set_to.setTextSize(25);


//            if (this.fromDateSet) {
//                String date = makeDateString(day, month, year);
//                this.set_to.setText(date);
//                this.set_to.setTextSize(25);
//            } else {
//                Log.d(TAG, "_____initDatePicker: (this.fromDateSet == false, toDate)");
//                Toast.makeText(this.getContext(), "Please set a from date first.", Toast.LENGTH_SHORT).show();
//            }

            // TODO: Make sure this.fromDateSet gets set back to false when you reenter the add functionality (it should since it is set to false in the onCreate...but just check).
            if (this.fromDateSet) {
                Log.d(TAG, "_____initDatePicker (this.fromDateSet == true, toDate)");
                this.toDate.set(year, month, day);

                // TODO: Get this to work!!!! DOES NOT WORK correctly!
                if (this.toDate.before(this.fromDate)) {
//                if (this.toDate.get(Calendar.YEAR) < this.fromDate.get(Calendar.YEAR) && this.toDate.get(Calendar.MONTH) < this.fromDate.get(Calendar.MONTH) && this.toDate.get(Calendar.DAY_OF_MONTH) < this.fromDate.get(Calendar.DAY_OF_MONTH)) {
                    Log.d(TAG, "_____initDatePicker: (this.toDate.before(this.fromDate)");
                    Log.d(TAG, "initDatePicker: this.fromDate = " + (this.fromDate.get(Calendar.MONTH) + 1) + "/" + this.fromDate.get(Calendar.DAY_OF_MONTH) + "/" + this.fromDate.get(Calendar.YEAR) + ", this.toDate = " + (this.toDate.get(Calendar.MONTH) + 1) + "/" + this.toDate.get(Calendar.DAY_OF_MONTH) + "/" + this.toDate.get(Calendar.YEAR));
                    Log.d(TAG, "initDatePicker: to date -- this.fromDate = " + this.fromDate);
                    Toast.makeText(this.getContext(), "Your to date has to be after your from date. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "_____initDatePicker: the to date was set correctly");
                    Log.d(TAG, "initDatePicker: this.fromDate = " + (this.fromDate.get(Calendar.MONTH) + 1) + "/" + this.fromDate.get(Calendar.DAY_OF_MONTH) + "/" + this.fromDate.get(Calendar.YEAR) + ", this.toDate = " + (this.toDate.get(Calendar.MONTH) + 1) + "/" + this.toDate.get(Calendar.DAY_OF_MONTH) + "/" + this.toDate.get(Calendar.YEAR));
                    String date = makeDateString(day, month, year);
                    this.set_to.setText(date);
                    this.set_to.setTextSize(25);
                }
            } else {
                Log.d(TAG, "_____initDatePicker: (this.fromDateSet == false, toDate)");
                Toast.makeText(this.getContext(), "Please set a from date first.", Toast.LENGTH_SHORT).show();
            }
        };

        this.to_datePickerDialog = new DatePickerDialog(getContext(), style, to_dateSetListener, year1, month1, day1);
    }
}