package edu.northeastern.elderberry.your_medication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

import edu.northeastern.elderberry.addMed.AddMedicationActivity;
import edu.northeastern.elderberry.LoginActivity;
import edu.northeastern.elderberry.MedicationTrackerActivity;
import edu.northeastern.elderberry.OnListItemClick;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.editMed.EditMedicationActivity;

public class YourMedicationsActivity extends AppCompatActivity {
    private static final String TAG = "YourMedicationsActivity";
    private MedicineAdapter medAdapter;
    private final ArrayList<MedicineRow> medicines = new ArrayList<>();
    private final ArrayList<String> medKey = new ArrayList<>();
    public static final String YOUR_MED_TO_EDIT_MED_KEY = "medKey";

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
        String user = "Gavin";
        DatabaseReference medicineDB = FirebaseDatabase.getInstance().getReference();
        medicineDB.child(user).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");
                medicines.clear();
                // Todo enable this when add medicine to db is auto
                //Iterator<DataSnapshot> it = snapshot.getChildren().iterator();
                //while (it.hasNext()) {
                //    DataSnapshot d = it.next();
                //    //Medicine md = d.getValue(Medicine.class);
                //    //System.out.println("medicine is " + d.toString());
                //}

                for (DataSnapshot d : snapshot.getChildren()) {
                    medKey.add(d.getKey());
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
        this.medAdapter = new MedicineAdapter(this.medicines);
        // Todo enable edits within recycler view
        // Todo reference https://stackoverflow.com/questions/48791643/edit-recyclerview-item-and-update-them-on-firebase
        OnListItemClick onListItemClick = position -> {
            Log.d(TAG, "_____onClick: ");
            // Todo include position information in click
            Intent intent = new Intent(YourMedicationsActivity.this, EditMedicationActivity.class);
            Log.d(TAG, "_____onCreate, OnListItemClick, prior to medKey " + medKey.get(position));
            intent.putExtra(YOUR_MED_TO_EDIT_MED_KEY, medKey.get(position));
            Log.d(TAG, "_____onCreate, OnListItemClick, post medKey");
            startActivity(intent);
        };
        this.medAdapter.setClickListener(onListItemClick);
        recyclerView.setAdapter(this.medAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
        //    @Override
        //    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        //        return false;
        //    }

        //    @Override
        //    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        //    }

        //    @Override
        //    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        //    }
        //});
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