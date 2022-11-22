package edu.northeastern.elderberry;

import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetTimesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO: Make it so once the user picks a time frequency the correct number of times and corresponding doses show up
// TODO: Make fields required
// TODO: Fix the layout design in landscape mode
public class SetTimesFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "SetTimesFragment";
    private static final String TIME_PICKER_TAG = "time picker";

    private TextView set_time;

    public SetTimesFragment() {
        Log.d(TAG, "_____SetTimesFragment");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SetTimesFragment.
     */
    public static SetTimesFragment newInstance() {
        Log.d(TAG, "_____newInstance");
        return new SetTimesFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_times, container, false);

        // Set time frequency functionality.
        // Get reference to the string array.
        Resources res = getResources();
        String[] time_frequencies = res.getStringArray(R.array.time_frequencies);
        // Create an array adapter and pass the context, drop down layout, and array.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, time_frequencies);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Get reference to the autocomplete text view
        AutoCompleteTextView autoCompleteTimeFreq = view.findViewById(R.id.setTimeFrequency);
        // set adapter to the autocomplete tv to the arrayAdapter
        autoCompleteTimeFreq.setAdapter(arrayAdapter);

        autoCompleteTimeFreq.setOnItemClickListener((parent, view1, position, id) -> {
            Log.d(TAG, "_____onItemClick");
            Toast.makeText(getContext(), "Clicked on item " + position + 1, Toast.LENGTH_SHORT).show();
        });

        //        // Set time and dose functionality.


//        ImageView time_picker = findViewById(R.id.time_picker);
//        time_picker.setOnClickListener(v -> {
//            DialogFragment timePicker = new TimePickerFragment();
//            timePicker.show(getSupportFragmentManager(), TIME_PICKER_TAG);
//        });
//
//        this.set_time = findViewById(R.id.set_time);

        return view;
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

        this.set_time.setText(getString(R.string.set_time, hourOfDay, st_min, am_pm));
    }
}