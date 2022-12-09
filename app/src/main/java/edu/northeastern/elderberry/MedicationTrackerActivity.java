package edu.northeastern.elderberry;

import static edu.northeastern.elderberry.util.DatetimeFormat.makeDateString;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import edu.northeastern.elderberry.addMed.AddMedicationActivity;
import edu.northeastern.elderberry.helpAndConfigs.AboutActivity;
import edu.northeastern.elderberry.helpAndConfigs.SettingsActivity;

import edu.northeastern.elderberry.dayview.MedicationDayview;
import edu.northeastern.elderberry.your_medication.YourMedicationsActivity;

public class MedicationTrackerActivity extends AppCompatActivity {
    private static final String TAG = "MedicationTrackerActivity";
    private FirebaseAuth mAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_medication_tracker);

        mAuth = FirebaseAuth.getInstance();
        if(!checkPermission()){
            getPermissions();
        }
        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        // Providing a subtitle for the ActionBar.
        assert actionBar != null;
        actionBar.setSubtitle(getString(R.string.medication_tracker));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        CalendarView calendarView = findViewById(R.id.calendar);

        // Add Listener in calendar.
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Log.d(TAG, "_____onCreate: calendarView.setOnDateChangeListener");
            String date = makeDateString(dayOfMonth, month, year);
            // Todo ask user if to navigate to the activity
            // https://developer.android.com/guide/topics/location/transitions reference this
            Intent intent = new Intent(this, MedicationDayview.class);
            startActivity(intent);
        });

        // BottomNavigationView functionality.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d(TAG, "_____onCreate: bottomNavigationView.setOnItemSelectedListener");
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startMedicationTrackerActivity();
                return true;
            } else if (itemId == R.id.add_med) {
                startAddMedicationActivity();
                return true;
            } else if (itemId == R.id.view_med) {
                startYourMedicationsActivity();
                return true;
            }
            return false;
        });
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationUtil.getMedicationInfo(getApplicationContext(), notificationManager);
    }

    private boolean checkPermission()
    {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void getPermissions()
    {
        int REQUEST_CODE = 9882;
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE);
    }


    private void startMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        onResume();
    }

    private void startAddMedicationActivity() {
        Log.d(TAG, "_____startAddMedicationActivity");
        Intent intent = new Intent(this, AddMedicationActivity.class);
        startActivity(intent);
    }

    private void startYourMedicationsActivity() {
        Log.d(TAG, "_____startYourMedicationsActivity");
        Intent intent = new Intent(this, YourMedicationsActivity.class);
        startActivity(intent);
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Method to control the operations that will happen when user clicks on the action buttons.
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "_____onOptionsItemSelected");
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.medicationHistory:
                Log.d(TAG, "_____onOptionsItemSelected (medicationHistory)");
                intent = new Intent(this, MedicationHistory.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                Log.d(TAG, "_____onOptionsItemSelected (settings)");
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Log.d(TAG, "_____onOptionsItemSelected (about)");
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.log_out:
                Log.d(TAG, "_____onOptionsItemSelected (logout)");

                // Set hasLoggedIn has false, since the user has now logged out.
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.apply();

                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}