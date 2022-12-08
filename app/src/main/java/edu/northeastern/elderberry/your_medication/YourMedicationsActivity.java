package edu.northeastern.elderberry.your_medication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.Objects;

import edu.northeastern.elderberry.LoginActivity;
import edu.northeastern.elderberry.MedicationTrackerActivity;
import edu.northeastern.elderberry.OnListItemClick;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.addMed.AddMedicationActivity;

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
        actionBar.setSubtitle(Html.fromHtml("<small>" + getString(R.string.medication_tracker) + "</small>", Html.FROM_HTML_MODE_LEGACY));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference medDatabase = userDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        Log.d(TAG, "onCreate: Retrieving user med db with user ID" + mAuth.getCurrentUser().getUid());
        //DatabaseReference medicineDB = FirebaseDatabase.getInstance().getReference();
        //medicineDB.child(user).addValueEventListener(new ValueEventListener() {
        medDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");
                medicines.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    // put all medicine in an arrayList
                    // sort the array list by from date
                    // another for loop
                    String id = d.getKey();
                    medKey.add(id);
                    // 2 Todo update this to using medicine class
                    MedicineRow medRow = new MedicineRow(id, String.valueOf(d.child("name").getValue()), String.valueOf(d.child("fromDate").getValue()), String.valueOf(d.child("toDate").getValue()));
                    medicines.add(medRow);
                }
                // 2 Todo We can sort the medicine before we show it, maybe sorting in db is better

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

        OnListItemClick onListItemClick = position -> {
            Log.d(TAG, "_____onClick: ");
            Intent intent = new Intent(YourMedicationsActivity.this, AddMedicationActivity.class);
            Log.d(TAG, "_____onCreate, OnListItemClick, prior to medKey " + medKey.get(position));
            intent.putExtra(YOUR_MED_TO_EDIT_MED_KEY, medKey.get(position));
            Log.d(TAG, "_____onCreate, OnListItemClick, post medKey");
            startActivity(intent);
        };
        this.medAdapter.setClickListener(onListItemClick);
        recyclerView.setAdapter(this.medAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a method to create item touch helper method for adding swipe to delete functionality.
        // In this we are specifying drag direction and position to right.
        // https://www.geeksforgeeks.org/swipe-to-delete-and-undo-in-android-recyclerview/
        // Future: will not actually delete medication from database (just tag it as deleted)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            // This method is called when the item is moved.
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Log.d(TAG, "_____onMove");
                return false;
            }

            // This method is called when we swipe our item to right direction.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(TAG, "_____onSwiped");
                // Here we are getting the item at a particular position.
                MedicineRow deletedMed = medicines.get(viewHolder.getAbsoluteAdapterPosition());

                // Get the position of the item.
                int position = viewHolder.getAbsoluteAdapterPosition();

                // Remove item from our array list.
                medicines.remove(viewHolder.getAbsoluteAdapterPosition());

                // Notify that the item is removed from adapter.
                medAdapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());

                // Display alert dialog with action.
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(YourMedicationsActivity.this);
                alertDialog.setTitle("Delete Medication");
                alertDialog.setMessage("Do you really want to delete this medication?");

                alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                    Log.d(TAG, "_____onSwiped: yes");

                    // The user wants to remove the medication. Need to remove it from the database.
                    medDatabase.child(deletedMed.getId()).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "_____onComplete: medication remove from the database succeeded");
                        } else {
                            Log.d(TAG, "_____onComplete: medication remove from the database failed");
                        }
                    });

                    dialogInterface.dismiss();
                });

                alertDialog.setNegativeButton("No", (dialogInterface, i) -> {
                    Log.d(TAG, "_____onSwiped: no");

                    // Adding on click listener to our action of snack bar. Add our item to array list with a position.
                    medicines.add(position, deletedMed);

                    // Notify item is added to our adapter class.
                    medAdapter.notifyItemInserted(position);
                    dialogInterface.dismiss();
                });

                alertDialog.show();
            }
            // Adding this to our recycler view.
        }).attachToRecyclerView(recyclerView);
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