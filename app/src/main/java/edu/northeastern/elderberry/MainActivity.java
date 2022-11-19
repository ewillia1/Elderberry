package edu.northeastern.elderberry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_main);

        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        // Providing a title fot the ActionBar.
        assert actionBar != null;
        actionBar.setTitle(R.string.app_name);

        // Providing a subtitle for the ActionBar.
        actionBar.setSubtitle(getString(R.string.medication_tracker));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false);

            if (hasLoggedIn) {
                startMedicationTrackerActivity();
            } else {
                startLoginActivity();
            }
        }, TIME_OUT);
    }

    private void startLoginActivity() {
        Log.d(TAG, "_____startLoginActivity");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        Intent intent = new Intent(this, MedicationTrackerActivity.class);
        startActivity(intent);
        finish();
    }
}