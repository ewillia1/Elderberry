package edu.northeastern.elderberry.helpAndConfigs;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import edu.northeastern.elderberry.R;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}