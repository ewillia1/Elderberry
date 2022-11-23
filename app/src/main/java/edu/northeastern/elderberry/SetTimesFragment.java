package edu.northeastern.elderberry;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetTimesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO: Make it so once the user picks a time frequency the correct number of times and corresponding doses show up
// TODO: Make fields required
// TODO: Fix the layout design in landscape mode
public class SetTimesFragment extends Fragment implements OnTimeDoseItemListener  {
    private static final String TAG = "SetTimesFragment";
    private static final String LIST_STATE = "list_state";

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
        // What happens when an time frequency is clicked on.
        autoCompleteTimeFreq.setOnItemClickListener((parent, view1, position, id) -> Log.d(TAG, "_____onItemClick: clicked on item " + position + 1));

        // Instantiate the ArrayList.
        ArrayList<TimeDoseItem> timeDoseItemArrayList = new ArrayList<>();

        // Temporary.
        timeDoseItemArrayList.add(new TimeDoseItem(1, "10:00", "2", "tabs"));

        // Instantiate the recyclerView.
        RecyclerView timeDoseRecyclerView = view.findViewById(R.id.recyclerView);

        // Restore ArrayList of linked items, if the screen is rotated.
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(LIST_STATE) != null) {
            timeDoseItemArrayList = savedInstanceState.getParcelableArrayList(LIST_STATE);
        }

        timeDoseRecyclerView.setHasFixedSize(true);
        timeDoseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeDoseRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Instantiate recyclerView adapter. Associate the adapter with the recyclerView.
        TimeDoseAdapter timeDoseAdapter = new TimeDoseAdapter(timeDoseItemArrayList, getContext(), this);
        timeDoseRecyclerView.setAdapter(timeDoseAdapter);

        return view;
    }

    @Override
    public void onTimeDoseItemClick(int position) {
        Log.d(TAG, "_____onTimeDoseItemClick: clicked item " + position + 1);
    }
}