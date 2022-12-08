package edu.northeastern.elderberry.addMed;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.elderberry.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetTimesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SetTimesFragment extends Fragment implements OnTimeDoseItemListener {
    private static final String TAG = "SetTimesFragment";
    private int numOfTimes;
    private TimeDoseAdapter timeDoseAdapter;
    private ItemViewModel viewModel;

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

        // init view model moved from onViewCreate to here
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        // Set unit functionality.
        // Get reference to the string array.
        Resources res = getResources();
        String[] units_array = res.getStringArray(R.array.units_array);
        // Create an array adapter and pass the context, drop down layout, and array.
        ArrayAdapter<String> arrayAdapterForUnits = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, units_array);
        arrayAdapterForUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get reference to the autocomplete text view.
        AutoCompleteTextView autoCompleteUnit = view.findViewById(R.id.setUnit);
        // Set adapter to the autocomplete tv to the arrayAdapter.
        autoCompleteUnit.setOnItemClickListener((parent, view12, position, id) -> {
            String unitSelection = (String) parent.getItemAtPosition(position);
            Log.d(TAG, "_____onItemClick: position = " + position + ", id = " + id + ", unitSelection = " + unitSelection);
            this.viewModel.setUnit(unitSelection);
        });

        // Set time frequency functionality.
        // Get reference to the string array.
        String[] time_frequencies = res.getStringArray(R.array.time_frequencies);
        // Create an array adapter and pass the context, drop down layout, and array.
        ArrayAdapter<String> arrayAdapterForFreq = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, time_frequencies);
        arrayAdapterForFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Get reference to the autocomplete text view.
        AutoCompleteTextView autoCompleteTimeFreq = view.findViewById(R.id.setTimeFrequency);

        // Instantiate the ArrayList.
        ArrayList<TimeDoseItem> timeDoseItemArrayList = new ArrayList<>();

        // Instantiate recyclerView adapter. Associate the adapter with the recyclerView.
        this.timeDoseAdapter = new TimeDoseAdapter(timeDoseItemArrayList, getContext(), this);

        // If user comes from your medication, auto fill the frequency
        AddMedicationActivity addMedicationActivity = (AddMedicationActivity) getActivity();
        assert addMedicationActivity != null;
        String editMedKey = addMedicationActivity.getEditMedKey();

        // pre-fill
        if (editMedKey != null) {
            int freq = viewModel.getTimeFreq().getValue();
            int pos = freq - 1;
            autoCompleteTimeFreq.setText(time_frequencies[pos]);
            autoCompleteUnit.setText(viewModel.getUnit().getValue());
            for (int i = 0; i < freq; i++) {
                timeDoseItemArrayList.add(new TimeDoseItem(pos, viewModel.getTime(i).getValue(), viewModel.getDose(i).getValue(), viewModel.getUnit().getValue()));
            }
        }

        // Set adapter to the autocomplete tv to the arrayAdapter.
        autoCompleteTimeFreq.setAdapter(arrayAdapterForFreq);
        autoCompleteUnit.setAdapter(arrayAdapterForUnits);

        // What happens when an time frequency is clicked on.
        autoCompleteTimeFreq.setOnItemClickListener((parent, view1, position, id) -> {
            Log.d(TAG, "_____onItemClick: clicked on item " + (position + 1));
            // Clear the finalTimeDoseItemArrayList.
            timeDoseItemArrayList.clear();

            // Clear timeDoseAdapter (clearing the RecyclerView).
            timeDoseAdapter.clear();

            // Clear the time and dose array in the view model.
            this.viewModel.clear();

            this.numOfTimes = position + 1;

            Log.d(TAG, "_____onCreateView: this.numOfTimes = " + this.numOfTimes);

            // Add number of cards in recycler view corresponding to the time frequency the user picked.
            for (int i = 0; i < this.numOfTimes; i++) {
                Log.d(TAG, "_____onCreateView: for loop iteration: " + i);
                //timeDoseItemArrayList.add(new TimeDoseItem(position));
                timeDoseItemArrayList.add(new TimeDoseItem(position));
            }
            this.viewModel.setTimeFreq(this.numOfTimes);
        });

        // Instantiate the recyclerView.
        RecyclerView timeDoseRecyclerView = view.findViewById(R.id.recyclerView);
        timeDoseRecyclerView.setHasFixedSize(true);
        timeDoseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeDoseRecyclerView.setItemAnimator(new DefaultItemAnimator());

        timeDoseRecyclerView.setAdapter(timeDoseAdapter);

        return view;
    }

    @Override
    public void onTimeDoseItemClick(int position) {
        Log.d(TAG, "_____onTimeDoseItemClick: clicked item " + (position + 1));
    }

    @Override
    public void timeWasAdded(int index, String time) {
        Log.d(TAG, "_____timeWasAdded: time = " + time + ", index = " + index);
        this.viewModel.setTime(index, time);
    }

    @Override
    public void doseWasAdded(int index, String dose) {
        Log.d(TAG, "_____doseWasAdded: dose = " + dose + ", index = " + index);
        this.viewModel.setDose(index, dose);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "_____onViewCreated");
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }
}