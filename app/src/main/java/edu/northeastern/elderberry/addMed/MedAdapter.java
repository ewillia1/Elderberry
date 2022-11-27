package edu.northeastern.elderberry.addMed;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

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

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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
        return this.totalTabs;
    }
}
