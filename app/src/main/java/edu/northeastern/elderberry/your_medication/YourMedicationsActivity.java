package edu.northeastern.elderberry.your_medication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
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

import edu.northeastern.elderberry.MyNotificationPublisher;
import edu.northeastern.elderberry.NotificationUtil;
import edu.northeastern.elderberry.OnListItemClick;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.addMed.AddMedicationActivity;

public class YourMedicationsActivity extends AppCompatActivity {
    public static final String YOUR_MED_TO_EDIT_MED_KEY = "medKey";
    public static final String YOUR_MED_KEY = "yourMedKey";
    private static final String TAG = "YourMedicationsActivity";
    private final ArrayList<MedicineRow> medicinesArrayList = new ArrayList<>();
    private final ArrayList<String> medKeyArrayList = new ArrayList<>();
    private MedicineAdapter medAdapter;

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
        medDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: starting");
                medicinesArrayList.clear();

                // Loop through snapshot to get the medicines to put in the ArrayList medicines.
                for (DataSnapshot d : snapshot.getChildren()) {
                    MedicineRow medRow = new MedicineRow(d.getKey(), String.valueOf(d.child("name").getValue()), String.valueOf(d.child("fromDate").getValue()), String.valueOf(d.child("toDate").getValue()));
                    medicinesArrayList.add(medRow);
                }

                // Sort medicines by from date for the RecyclerView.
                medicinesArrayList.sort(new SortMedicineRow());

                // Loop through the sorted medicines to add their IDs to the ArrayList medKey.
                // We need this loop here instead of in the above for each loop so that the indices align with the RecyclerView.
                String medicineId;
                for (MedicineRow medicineRow : medicinesArrayList) {
                    // medKeyArrayList is an ArrayList of medicine keys (Strings).
                    medicineId = medicineRow.getId();
                    medKeyArrayList.add(medicineId);
                }

                medAdapter.notifyDataSetChanged();
                Log.d(TAG, "_____onDataChange: finished");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "_____onCancelled: ");
            }
        });

        // Set RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.yourMedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Passing an array into the recyclerview adapter.
        this.medAdapter = new MedicineAdapter(this.medicinesArrayList);

        // init a class which grab the position arg from child
        // and use the key to grab the correct position of the medicine key
        OnListItemClick onListItemClick = position -> {
            Log.d(TAG, "_____onClick: position = " + position + ", medKey = " + medKeyArrayList);
            Intent intent = new Intent(YourMedicationsActivity.this, AddMedicationActivity.class);
            Log.d(TAG, "_____onCreate, OnListItemClick, prior to medKey " + medKeyArrayList.get(position));
            intent.putExtra(YOUR_MED_TO_EDIT_MED_KEY, medKeyArrayList.get(position));
            Log.d(TAG, "_____onCreate, OnListItemClick, post medKey");
            startActivity(intent);
        };

        this.medAdapter.setClickListener(onListItemClick);
        recyclerView.setAdapter(this.medAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a method to create item touch helper method for adding swipe to delete functionality.
        // In this we are specifying drag direction and position to right.
        // https://www.geeksforgeeks.org/swipe-to-delete-and-undo-in-android-recyclerview/
        // Future application: will not actually delete medication from database (just tag it as deleted).
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            // This method is called when the item is moved.
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Log.d(TAG, "_____onMove");
                return false;
            }

            // This method is called when we swipe our item to left direction.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(TAG, "_____onSwiped");
                // Here we are getting the item at a particular position.
                MedicineRow deletedMed = medicinesArrayList.get(viewHolder.getAbsoluteAdapterPosition());

                // Get the position of the item.
                int position = viewHolder.getAbsoluteAdapterPosition();

                // Remove item from our array list.
                medicinesArrayList.remove(viewHolder.getAbsoluteAdapterPosition());

                // Remove item id from our other array list.
                medKeyArrayList.remove(deletedMed.getId());

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
                    MyNotificationPublisher.deletePendingIntents();
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationUtil.getMedicationInfo(getApplicationContext(), notificationManager);
                    dialogInterface.dismiss();
                });

                alertDialog.setNegativeButton("No", (dialogInterface, i) -> {
                    Log.d(TAG, "_____onSwiped: no");

                    // Add our item to array list with a position.
                    medicinesArrayList.add(position, deletedMed);

                    // Add our item id to other array list with a position.
                    medKeyArrayList.add(position, deletedMed.getId());

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
        intent.putExtra(YOUR_MED_KEY, true);
        startActivity(intent);

        // Want to finish this activity so that when add or edit is pressed onCreate for this activity is called again.
        // So the array lists are sorted correctly.
        finish();
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
            MyNotificationPublisher.deletePendingIntents();
            FirebaseAuth.getInstance().signOut();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}