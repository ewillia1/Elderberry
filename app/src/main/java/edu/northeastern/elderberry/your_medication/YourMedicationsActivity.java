package edu.northeastern.elderberry.your_medication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.northeastern.elderberry.AddMedicationActivity;
import edu.northeastern.elderberry.LoginActivity;
import edu.northeastern.elderberry.MedicationTrackerActivity;
import edu.northeastern.elderberry.R;

public class YourMedicationsActivity extends AppCompatActivity {
    private static final String TAG = "YourMedicationsActivity";
    private MedicineAdapter medAdapter;
    private ArrayList<MedicineRow> medicines = new ArrayList<>();
    private DatabaseReference medicineDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_your_medications);

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

        // BottomNavigationView functionality.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
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

        // init db data
        // Todo to make db query more generic
        this.medicineDB = FirebaseDatabase.getInstance().getReference();
        this.medicineDB.child("Gavin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");
                medicines.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    //System.out.println(d.child("name"));
                    //System.out.println(d.child("fromDate"));
                    //System.out.println(d.child("toDate"));
                    //System.out.println(d.child("name").getValue());
                    //MedicineRow medRow = new MedicineRow((String) d.child("name").getValue(), (String) d.child("fromDate").getValue(), (String) d.child("toDate").getValue());
                    MedicineRow medRow = new MedicineRow(String.valueOf(d.child("name").getValue()), String.valueOf(d.child("fromDate").getValue()), String.valueOf(d.child("toDate").getValue()));
                    medicines.add(medRow);
                }
                medAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "_____onCancelled: ");
                //Toast.makeText(YourMedicationsActivity.this, "Error: Unable to get access to Database", Toast.LENGTH_SHORT).show();
            }
        });

        // Set RecyclerView
        RecyclerView recyclerView = findViewById(R.id.yourMedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // passing an array into the recyclerview adapter
        // Test data
        //this.medicines.add(new MedicineRow("Vitamin A", "20221001", "20221101"));
        //this.medicines.add(new MedicineRow("Vitamin B", "20221101", "20221201"));
        //this.medicines.add(new MedicineRow("Vitamin C", "20221201", "20230101"));
        this.medAdapter = new MedicineAdapter(this.medicines);
        recyclerView.setAdapter(this.medAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Todo enable edits within recycler view
        // Todo edit the UI of the recycler view to display the "right" info, include the field name
    }

    private void startMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        Intent intent = new Intent(this, MedicationTrackerActivity.class);
        startActivity(intent);
    }

    private void startAddMedicationActivity() {
        Log.d(TAG, "_____startAddMedicationActivity");
        Intent intent = new Intent(this, AddMedicationActivity.class);
        startActivity(intent);
    }

    private void startYourMedicationsActivity() {
        Log.d(TAG, "_____startYourMedicationsActivity");
        onResume();
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Method to control the operations that will happen when user clicks on the action buttons.
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "_____onOptionsItemSelected");
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.log_out) {
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
        }
        return super.onOptionsItemSelected(item);
    }
}