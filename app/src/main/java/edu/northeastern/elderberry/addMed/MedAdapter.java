package edu.northeastern.elderberry.addMed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MedAdapter extends FragmentStateAdapter {
    private static final String TAG = "MedAdapter";
    private final int totalTabs;

    public MedAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "_____createFragment");
        switch (position) {
            case 0:
                return MedInfoFragment.newInstance();
            case 1:
                return SetDatesFragment.newInstance();
            default:
                return SetTimesFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "_____getItemCount");
        return this.totalTabs;
    }
}
