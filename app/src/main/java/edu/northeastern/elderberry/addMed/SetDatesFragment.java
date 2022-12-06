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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import edu.northeastern.elderberry.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetDatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SetDatesFragment extends Fragment {
    private static final String TAG = "SetDatesFragment";
    private static final int FONT_SIZE = 25;
    private static final int DEFAULT_THEME_STYLE = 0;
    private DatePickerDialog from_datePickerDialog;
    private DatePickerDialog to_datePickerDialog;
    private TextView set_from;
    private TextView set_to;

    private boolean fromDateSet;
    private int numOfTimeSetFromDate;
    private Calendar fromDate;
    private Calendar toDate;
    private ItemViewModel viewModel;
    private String fromDate_db;
    private String toDate_db;
    private String editMedKey;

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
        this.numOfTimeSetFromDate = 0;
        this.fromDate = Calendar.getInstance();
        this.toDate = Calendar.getInstance();
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
            this.from_datePickerDialog.show();
        });
        ImageView to_image = view.findViewById(R.id.to_image);
        to_image.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (to_image)");
            this.to_datePickerDialog.show();
        });
        
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        initDatePicker();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "_____onViewCreated");
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }

    private void initDatePicker() {
        Log.d(TAG, "_____initDatePicker");
        // If we calling from your Med, then we should pre-fill the from & to TextView
        // retrieve medKey selected from the yourMedication activity
        AddMedicationActivity addMedicationActivity = (AddMedicationActivity) getActivity();
        editMedKey = addMedicationActivity.getEditMedKey();
        if (editMedKey != null) {
            Log.d(TAG, "initDatePicker: non null editMedKey");
            set_from.setText(this.viewModel.getFromDate().getValue());
            set_to.setText(this.viewModel.getToDate().getValue());
        }
        
        // From date.
        DatePickerDialog.OnDateSetListener from_dateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "_____initDatePicker");
            String date = makeDateString(day, month, year);
            this.fromDateSet = true;
            this.set_from.setText(date);
            this.set_from.setTextSize(FONT_SIZE);
            Log.d(TAG, "_____initDatePicker fromDate -- this.fromDate = " + this.fromDate);
            this.fromDate.set(year, month, day); // Check with Elizabeth on if this is a duplication
            this.fromDate_db = makeDateString(day, month, year);
            this.viewModel.setFromDate(this.fromDate_db);
            Log.d(TAG, "_____initDatePicker: this.fromDate_db = " + this.fromDate_db);
            Log.d(TAG, "_____initDatePicker fromDate -- this.fromDate = " + this.fromDate);

            if (this.numOfTimeSetFromDate > 0 && this.toDate.before(this.fromDate)) {
                Log.d(TAG, "_____initDatePicker: this.numOfTimeSetFromDate > 1 && this.toDate.before(this.fromDate)");
                this.set_to.setText("");
                Toast.makeText(this.getContext(), "Please re-enter a to date that is after your from date.", Toast.LENGTH_SHORT).show();
            }

            this.numOfTimeSetFromDate++;
        };

        Calendar calendar = Calendar.getInstance();
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        int style = DEFAULT_THEME_STYLE;
        // Take input from user on date selected from calendar.
        this.from_datePickerDialog = new DatePickerDialog(getContext(), style, from_dateSetListener, year1, month1, day1);

        // To date.
        DatePickerDialog.OnDateSetListener to_dateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "_____initDatePicker");
            if (this.fromDateSet) {
                Log.d(TAG, "_____initDatePicker (this.fromDateSet == true, toDate)");
                this.toDate.set(year, month, day);
                this.toDate_db = makeDateString(day, month, year);
                Log.d(TAG, "_____initDatePicker: this.toDate_db = " + this.toDate_db);
                this.viewModel.setToDate(this.toDate_db);
                if (this.toDate.before(this.fromDate)) {
                    Log.d(TAG, "_____initDatePicker: IF -- try to set the to do again");
                    Log.d(TAG, "_____initDatePicker: this.fromDate = " + (this.fromDate.get(Calendar.MONTH) + 1) + "/" + this.fromDate.get(Calendar.DAY_OF_MONTH) + "/" + this.fromDate.get(Calendar.YEAR) + ", this.toDate = " + (this.toDate.get(Calendar.MONTH) + 1) + "/" + this.toDate.get(Calendar.DAY_OF_MONTH) + "/" + this.toDate.get(Calendar.YEAR));
                    Toast.makeText(this.getContext(), "Your to date has to be after your from date. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "_____initDatePicker: ELSE -- the to date was set correctly");
                    Log.d(TAG, "_____initDatePicker: this.fromDate = " + (this.fromDate.get(Calendar.MONTH) + 1) + "/" + this.fromDate.get(Calendar.DAY_OF_MONTH) + "/" + this.fromDate.get(Calendar.YEAR) + ", this.toDate = " + (this.toDate.get(Calendar.MONTH) + 1) + "/" + this.toDate.get(Calendar.DAY_OF_MONTH) + "/" + this.toDate.get(Calendar.YEAR));
                    String date = makeDateString(day, month, year);
                    this.set_to.setText(date);
                    this.set_to.setTextSize(FONT_SIZE);
                }
            } else {
                Log.d(TAG, "_____initDatePicker: (this.fromDateSet == false, toDate)");
                Toast.makeText(this.getContext(), "Please set a from date first.", Toast.LENGTH_SHORT).show();
            }
        };

        this.to_datePickerDialog = new DatePickerDialog(getContext(), style, to_dateSetListener, year1, month1, day1);
    }
}