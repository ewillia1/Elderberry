package edu.northeastern.elderberry.addMed;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

// TODO: Make it so once the user picks a time frequency the correct number of times and corresponding doses show up.
// TODO: Make fields required.
// TODO: Save unit, time(s), and dose(s)...and maybe the time frequency, if necessary (use TimeDoseViewHolder.java for this).
public class SetTimesFragment extends Fragment implements OnTimeDoseItemListener {
    private static final String TAG = "SetTimesFragment";
    private int numOfTimes;
    private TimeDoseAdapter timeDoseAdapter;

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

        // Set dose functionality.
        // Get reference to the string array.
        Resources res = getResources();
        String[] units_array = res.getStringArray(R.array.units_array);
        // Create an array adapter and pass the context, drop down layout, and array.
        ArrayAdapter<String> arrayAdapterForUnits = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, units_array);
        arrayAdapterForUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Get reference to the autocomplete text view.
        AutoCompleteTextView autoCompleteUnit = view.findViewById(R.id.setUnit);
        // Set adapter to the autocomplete tv to the arrayAdapter.
        autoCompleteUnit.setAdapter(arrayAdapterForUnits);

        // Set time frequency functionality.
        // Get reference to the string array.
        String[] time_frequencies = res.getStringArray(R.array.time_frequencies);
        // Create an array adapter and pass the context, drop down layout, and array.
        ArrayAdapter<String> arrayAdapterForFreq = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, time_frequencies);
        arrayAdapterForFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Get reference to the autocomplete text view.
        AutoCompleteTextView autoCompleteTimeFreq = view.findViewById(R.id.setTimeFrequency);
        // Set adapter to the autocomplete tv to the arrayAdapter.
        autoCompleteTimeFreq.setAdapter(arrayAdapterForFreq);

        // Instantiate the ArrayList.
        ArrayList<TimeDoseItem> timeDoseItemArrayList = new ArrayList<>();

        // Instantiate recyclerView adapter. Associate the adapter with the recyclerView.
        this.timeDoseAdapter = new TimeDoseAdapter(timeDoseItemArrayList, getContext(), this);

        // What happens when an time frequency is clicked on.
        autoCompleteTimeFreq.setOnItemClickListener((parent, view1, position, id) -> {
            Log.d(TAG, "_____onItemClick: clicked on item " + (position + 1));
            // Clear the finalTimeDoseItemArrayList.
            timeDoseItemArrayList.clear();

            // Clear timeDoseAdapter (clearing the RecyclerView).
            timeDoseAdapter.clear();

            switch (position) {
                case 0:
                    this.numOfTimes = 1;
                    break;
                case 1:
                    this.numOfTimes = 2;
                    break;
                case 2:
                    this.numOfTimes = 3;
                    break;
                case 3:
                    this.numOfTimes = 4;
                    break;
                case 4:
                    this.numOfTimes = 5;
                    break;
                case 5:
                    this.numOfTimes = 6;
                    break;
                case 6:
                    this.numOfTimes = 7;
                    break;
                case 7:
                    this.numOfTimes = 8;
                    break;
                case 8:
                    this.numOfTimes = 9;
                    break;
                case 9:
                    this.numOfTimes = 10;
                    break;
                case 10:
                    this.numOfTimes = 11;
                    break;
                case 11:
                    this.numOfTimes = 12;
                    break;
                default:
                    Toast.makeText(getContext(), "An error occurred. Somehow you clicked a menu item that does not exist.", Toast.LENGTH_SHORT).show();
            }

            for (int i = 0; i < this.numOfTimes; i++) {
                timeDoseItemArrayList.add(new TimeDoseItem(position));
            }
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
        Log.d(TAG, "_____onTimeDoseItemClick: clicked item " + position + 1);
    }
}