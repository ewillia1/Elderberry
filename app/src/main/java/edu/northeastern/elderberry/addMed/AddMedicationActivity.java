package edu.northeastern.elderberry.addMed;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.northeastern.elderberry.Medicine;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.your_medication.YourMedicationsActivity;

public class AddMedicationActivity extends AppCompatActivity {

    private static final String TAG = "AddMedicationActivity";
    private static final int MAX_INT = 12;
    private DatabaseReference userDatabase;
    private FirebaseAuth mAuth;
    private ItemViewModel viewModel;
    private String editMedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.add_med_main);

        this.userDatabase = FirebaseDatabase.getInstance().getReference();
        // Initialize Firebase Auth.
        // Get the shared instance of the FirebaseAuth object.
        this.mAuth = FirebaseAuth.getInstance();

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
            if (itemId == R.id.cancel_add) {
                finish();
                return true;
            } else if (itemId == R.id.add_med) {
                // Check to see if all the required fields are filled out. If they are, go ahead and add the medication
                // to the database, if not tell the user they need to fill in all required fields.
                if (filledInRequiredFields()) {
                    // Add fields to database.
                    Log.d(TAG, "_____onCreate: Successful add.");
                    doAddDataToDb();
                    Toast.makeText(AddMedicationActivity.this, R.string.successful_add, Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                } else {
                    Log.d(TAG, "_____onCreate: Unsuccessful add. Need to fill in all required fields.");
                    Toast.makeText(this, "Please fill in all required fields before clicking add.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        // TabLayout functionality.
        TabLayout tabLayout = findViewById(R.id.add_med_tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        final MedAdapter adapter = new MedAdapter(getSupportFragmentManager(), getLifecycle(), tabLayout.getTabCount());
        viewPager2.setAdapter(adapter);

        HashMap<Integer, String> tabNames = new HashMap<>();
        tabNames.put(0, getString(R.string.med_info));
        tabNames.put(1, getString(R.string.set_dates));
        tabNames.put(2, getString(R.string.set_times));
        // Needed so that not only the selecting of the tabs works as expected, but also the swiping of the tabs.
        // https://developer.android.com/guide/navigation/navigation-swipe-view-2#java
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabNames.get(position))).attach();

        // ViewModel functionality.
        this.viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        this.viewModel.initializeTimeArray();
        this.viewModel.initializeDoseArray();
        this.viewModel.getMedName().observe(this, s -> Log.d(TAG, "_____onChanged: med name entered = " + s));
        this.viewModel.getFromDate().observe(this, s -> Log.d(TAG, "_____onChanged: from date entered = " + s));
        this.viewModel.getToDate().observe(this, s -> Log.d(TAG, "_____onChanged: to date entered = " + s));
        this.viewModel.getTimeFreq().observe(this, s -> Log.d(TAG, "_____onChanged: time frequency entered = " + s));
        this.viewModel.getUnit().observe(this, s -> Log.d(TAG, "_____onChanged: unit entered = " + s));

        for (int i = 0; i < MAX_INT; i++) {
            int finalI = i;
            this.viewModel.getTime(i).observe(this, s -> Log.d(TAG, "_____onChanged: time " + (finalI + 1) + " entered = " + s));
            this.viewModel.getDose(i).observe(this, s -> Log.d(TAG, "_____onChanged: dose " + (finalI + 1) + " entered = " + s));
        }

        // get Intent from your Medication
        editMedKey = getIntent().getStringExtra(YourMedicationsActivity.YOUR_MED_TO_EDIT_MED_KEY);
        retrieveMedData(editMedKey);
    }

    private void doAddDataToDb() {
        FirebaseUser user = this.mAuth.getCurrentUser();
        assert user != null;
        Log.d(TAG, "_____doAddDataToDb: user.getUid() = " + user.getUid());
        DatabaseReference databaseReference = this.userDatabase.child(user.getUid());

        // Get reference to medication node. And add the values to it.
        DatabaseReference db = databaseReference.push();
        db.setValue(new Medicine(this.viewModel.getMedName().getValue(),
                this.viewModel.getInformation().getValue(),
                this.viewModel.getFromDate().getValue(),
                this.viewModel.getToDate().getValue(),
                this.viewModel.getUnit().getValue()));
        List<String> timeList = this.viewModel.getTimeStringArray();
        List<String> doseList = this.viewModel.getDoseStringArray();
        // Todo add time and taken

        Log.d(TAG, "_____doAddDataToDb: db.getKey() = " + db.getKey());
        databaseReference.child(Objects.requireNonNull(db.getKey())).child("time").push().setValue(timeList);
        databaseReference.child(Objects.requireNonNull(db.getKey())).child("dose").push().setValue(doseList);
    }

    private boolean filledInRequiredFields() {
        Log.d(TAG, "_____filledInRequiredFields");
        if (this.viewModel.getMedName().getValue() == null || this.viewModel.getFromDate().getValue() == null
                || this.viewModel.getToDate().getValue() == null || this.viewModel.getTimeFreq() == null || this.viewModel.getUnit().getValue() == null) {
            Log.d(TAG, "filledInRequiredFields: (a field is null) false");
            return false;
        } else if (this.viewModel.getMedName().getValue().isBlank() || this.viewModel.getMedName().getValue().isEmpty() ||
                this.viewModel.getFromDate().getValue().isBlank() || this.viewModel.getFromDate().getValue().isEmpty() ||
                this.viewModel.getToDate().getValue().isBlank() || this.viewModel.getToDate().getValue().isEmpty() ||
                Objects.requireNonNull(this.viewModel.getTimeFreq().getValue()).isBlank() || this.viewModel.getTimeFreq().getValue().isEmpty() ||
                this.viewModel.getUnit().getValue().isBlank() || this.viewModel.getUnit().getValue().isEmpty()) {
            Log.d(TAG, "filledInRequiredFields: (a non-time/dose field is blank or empty) false");
            return false;
        }
    }
    //for (var d: medDatabase.getRoot().child(editMedKey)) {
    //
    //}
    //medDatabase.addValueEventListener(new ValueEventListener() {
    //    @Override
    //    public void onDataChange(@NonNull DataSnapshot snapshot) {
    //        for (DataSnapshot d: snapshot.getChildren()){
    //            Log.d(TAG, "______onDataChange: d is " + d.toString());
    //        }

    //    }

    //    @Override
    //    public void onCancelled(@NonNull DatabaseError error) {

    //    }
    //});

    /**
     * Based on the user selection from yourMedication, this function retrieve the corresponding
     * data from the database and pass these data to the viewModel so that other fragments
     * can use these data to pre-fill the fields
     * @param editMedKey
     */

    private void retrieveMedData(String editMedKey) {
        Log.d(TAG, "_____retrieveMedData: editMedKey is " + editMedKey);
        if (editMedKey == null) return;

        // Todo revert to using actual data
        DatabaseReference medDatabase = this.userDatabase.child(this.mAuth.getCurrentUser().getUid());
        //DatabaseReference medDatabase = this.userDatabase.child("Gavin");

        medDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: snapshot getChildren returns" + snapshot.child(editMedKey));
                // Todo remove hardcode
                // Todo fine tune medication activity to represent the data structure in the database
                // Todo retrieve all fields and pass it to viewModel
                String medName = String.valueOf(snapshot.child(editMedKey).child("name").getValue());
                String fromDate = String.valueOf(snapshot.child(editMedKey).child("fromDate").getValue());
                String toDate = String.valueOf(snapshot.child(editMedKey).child("toDate").getValue());
                String unit = String.valueOf(snapshot.child(editMedKey).child("unit").getValue());
                String information = String.valueOf(snapshot.child(editMedKey).child("information").getValue());
                // Todo extract array from the database
                //String[] time = String.valueOf(snapshot.child(editMedKey).child("time").getValue());
                //String dose = String.valueOf(snapshot.child(editMedKey).child("dose").getValue());
                viewModel.setMedName(medName);
                viewModel.setFromDate(fromDate);
                viewModel.setToDate(toDate);
                viewModel.setUnit(unit);
                viewModel.setInformation(information);
                // Todo activate this once we transfer to real data
                //Medicine med = snapshot.getValue(Medicine.class);
                //Log.d(TAG, "onDataChange: med retrieved from db is " + med.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * This function enables hosted fragments to access medication key the user has selected.
     * Fragments then subsequently retrieve the right medication information
     * @return the hashed key of the medication selected in the database
     */
    public String getEditMedKey() {
        return this.editMedKey;
    }

}

        ArrayList<String> timeList = this.viewModel.getTimeStringArray();
        ArrayList<String> doseList = this.viewModel.getDoseStringArray();

        Log.d(TAG, "____filledInRequiredFields: timeList = " + timeList + ",\n doseList = " + doseList);

        // We know that this.viewModel.getTimeFreq().getValue() is not null, blank, or empty if it gets down to here.
        int numOfTimesAndDoses = Integer.parseInt(this.viewModel.getTimeFreq().getValue());
        Log.d(TAG, "_____filledInRequiredFields: numOfTimesAndDoses = " + numOfTimesAndDoses);
        for (int i = 0; i < numOfTimesAndDoses; i++) {
            if (timeList.get(i) == null || doseList.get(i) == null || timeList.get(i).isBlank() || timeList.get(i).isEmpty() ||
                    doseList.get(i).isBlank() || doseList.get(i).isEmpty()) {
                Log.d(TAG, "_____filledInRequiredFields: (a field is null or blank or empty) false");
                return false;
            }
        }

        Log.d(TAG, "_____filledInRequiredFields: true");
        return true;
    }
}