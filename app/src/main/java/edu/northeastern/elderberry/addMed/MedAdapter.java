package edu.northeastern.elderberry.addMed;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// TODO: Ask about createFragment not returning null.
public class MedAdapter extends FragmentStateAdapter {
    private int totalTabs;

    public MedAdapter(Context context, FragmentManager fragmentManager, Lifecycle lifecycle, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.totalTabs = totalTabs;
    }

    public MedAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MedAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public MedAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    // TODO: Fix null issue!!!!
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return MedInfoFragment.newInstance();
            case 1:
                return SetDatesFragment.newInstance();
            case 2:
                return SetTimesFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return this.totalTabs;
    }
}
