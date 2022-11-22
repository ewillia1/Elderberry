package edu.northeastern.elderberry;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO: Fix the layout design in landscape mode
// TODO: Make fields required
// TODO: Fix the layout design in landscape mode
public class MedInfoFragment extends Fragment {
    private static final String TAG = "MedInfoFragment";

    public MedInfoFragment() {
        Log.d(TAG, "_____MedInfoFragment");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MedInfoFragment.
     */
    public static MedInfoFragment newInstance() {
        Log.d(TAG, "_____newInstance");
        return new MedInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_med_info, container, false);
        NestedScrollView scrollView = view.findViewById(R.id.nestedScrollView1);

        scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> v.getParent().requestDisallowInterceptTouchEvent(true));

        return view;
    }
}