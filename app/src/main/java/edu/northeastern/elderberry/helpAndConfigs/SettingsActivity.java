package edu.northeastern.elderberry.helpAndConfigs;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.elderberry.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_settings);

        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        // Providing a subtitle for the ActionBar.
        assert actionBar != null;
        actionBar.setSubtitle(Html.fromHtml("<small>" + getString(R.string.medication_tracker) + "</small>", Html.FROM_HTML_MODE_LEGACY));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // https://developer.android.com/develop/ui/views/components/settings
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MySettingsFragment())
                .commit();
    }
}