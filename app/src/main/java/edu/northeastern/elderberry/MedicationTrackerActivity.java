package edu.northeastern.elderberry;

import static edu.northeastern.elderberry.util.DatetimeFormat.makeDateString;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        getMedicationInfo();
    }

    private void scheduleMedicationNotifications(List<MedicineDoseTime> medicines)  {
        List<DateTimeDose> dates = new ArrayList<>();
        for(MedicineDoseTime doseTime: medicines) {
            String fromDate = doseTime.getFromDate();
            List<String> times = new ArrayList<>(doseTime.getTime().values()).get(0);
            List<String> doses = new ArrayList<>(doseTime.getDose().values()).get(0);

            SimpleDateFormat dateTimeFormatter =  new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
            SimpleDateFormat toDateTimeFormatter =  new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);

            int i = 0;
            for(String s: times) {
                String fromDateString = fromDate + " " + s;
                String toDateString = doseTime.getToDate() + " " + "11:59 PM";
                try {
                    DateTimeDose dateTimeDose = new DateTimeDose();
                    dateTimeDose.setFromTime(dateTimeFormatter.parse(fromDateString));
                    dateTimeDose.setToDate(toDateTimeFormatter.parse(toDateString));
                    dateTimeDose.setName(doseTime.getName());
                    dateTimeDose.setDose(Integer.parseInt(doses.get(i)));
                    i++;
                    dates.add(dateTimeDose);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("test");
        }
        for (DateTimeDose date: dates) {
            MyNotificationPublisher.setAlarm(date, this);
        }
    }

    private List<MedicineDoseTime> getMedicationInfo() {
        DatabaseReference userDB;
        List<MedicineDoseTime> medicineList = new ArrayList<>();

        userDB = FirebaseDatabase.getInstance().getReference();
        String userId = mAuth.getCurrentUser().getUid();

        // Todo to provide the correct username based on log-in info
        userDB.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                medicineList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);
                    medicineList.add(medicineDoseTime);
                }
                scheduleMedicationNotifications(medicineList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return null;
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