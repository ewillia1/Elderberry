package edu.northeastern.elderberry.helpAndConfigs;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;

import edu.northeastern.elderberry.R;

public class MySettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = "MySettingsFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.d(TAG, "_____onCreatePreferences");
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}