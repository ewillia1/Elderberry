package edu.northeastern.elderberry.editMed;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import edu.northeastern.elderberry.Medicine;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.addMed.ItemViewModel;
import edu.northeastern.elderberry.addMed.MedAdapter;
import edu.northeastern.elderberry.your_medication.YourMedicationsActivity;

// TODO: Add database functionality and check to see all required fields are filled in.
// TODO: Get the add button to work.
// TODO: Get focus to change when keyboard is collapsed.
// TODO: How to get time picker to show hour first and not minute (happens at time 7).
// TODO: Click enter in time recycler.
// TODO: Try to recreate losing times and doses and figure out why that is happening.
public class EditMedicationActivity extends AppCompatActivity {

    private static final String TAG = "EditMedicationActivity";
    private DatabaseReference userDatabase;
    private FirebaseAuth mAuth;
    private ItemViewModel viewModel;
    private boolean medNameComplete;
    private boolean medInfoComplete;
    private boolean medFromDateComplete;
    private boolean medToDateComplete;
    private boolean medUnitComplete;
    private boolean allRequiredFieldsComplete;
    private String editMedKey;
    // Todo 2. populate this through the activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.add_med_main);
        this.medNameComplete = false;
        this.medInfoComplete = false;
        this.medFromDateComplete = false;
        this.medToDateComplete = false;
        this.medUnitComplete = false;
        this.allRequiredFieldsComplete = false;

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
                Toast.makeText(EditMedicationActivity.this, R.string.successful_add, Toast.LENGTH_SHORT).show();
                // TODO!!!
                if (true) {
                    // Add fields to database.
                    doAddDataToDb();
                } else {
                    Toast.makeText(this, "Please fill in all required fields before hitting add.", Toast.LENGTH_SHORT).show();
                }
                finish();
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
        this.viewModel.getMedName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged: med name entered = " + s);
            }
        });
        this.viewModel.getFromDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "_____onChanged: from date entered = " + s);
            }
        });
        this.viewModel.getToDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "_____onChanged: to date entered = " + s);
            }
        });
        this.viewModel.getUnit().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "_____onChanged: unit entered = " + s);
            }
        });

        for (int i = 0; i < 12; i++) {
            int finalI = i;
            this.viewModel.getTime(i).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Log.d(TAG, "_____onChanged: time " + (finalI + 1) + " entered = " + s);
                }
            });
            this.viewModel.getDose(i).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Log.d(TAG, "_____onChanged: dose " + (finalI + 1) + " entered = " + s);
                }
            });
        }

        // get Intent from your Medication
        editMedKey = getIntent().getStringExtra(YourMedicationsActivity.YOUR_MED_TO_EDIT_MED_KEY);
        retrieveMedData(editMedKey);
        //medDatabase.addChildEventListener(new ChildEventListener() {
        //    @Override
        //    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        //        for (DataSnapshot d : snapshot.getChildren()) {
        //            Log.d(TAG, "onChildAdded: d is " + d.toString());
        //        }
        //    }

        //    @Override
        //    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        //    }

        //    @Override
        //    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
        //        for (DataSnapshot d : snapshot.getChildren()) {
        //            Log.d(TAG, "onChildRemoved: ");
        //        }
        //    }

        //    @Override
        //    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        //        for (DataSnapshot d : snapshot.getChildren()) {
        //            Log.d(TAG, "onChildMoved: ");
        //        }

        //    }

        //    @Override
        //    public void onCancelled(@NonNull DatabaseError error) {

        //    }
        //});
    }

    private void doAddDataToDb() {
        FirebaseUser user = this.mAuth.getCurrentUser();
        assert user != null;
        Log.d(TAG, "_____doAddDataToDb: user.getUid() = " + user.getUid());
        DatabaseReference databaseReference = this.userDatabase.child(user.getUid());
        Log.d(TAG, "_____doAddDataToDb: databaseReference.getKey() = " + databaseReference.getKey());
        DatabaseReference db = databaseReference.push();
        db.setValue(new Medicine(this.viewModel.getMedName().getValue(),
                this.viewModel.getInformation().getValue(),
                this.viewModel.getFromDate().getValue(),
                this.viewModel.getToDate().getValue(),
                this.viewModel.getUnit().getValue()));

        Log.d(TAG, "_____doAddDataToDb: db.getKey() = " + db.getKey());
        for (int i = 0; i < 12; i++) {
            databaseReference.child(Objects.requireNonNull(db.getKey())).child("time").child("time" + i).push().setValue(this.viewModel.getTime(i).getValue());
        }

        for (int j = 0; j < 12; j++) {
            databaseReference.child(Objects.requireNonNull(db.getKey())).child("dose").child("dose" + j).push().setValue(this.viewModel.getDose(j).getValue());
        }
    }

    private void retrieveMedData(String editMedKey) {
        Log.d(TAG, "_____retrieveMedData: editMedKey is " + editMedKey);
        if (editMedKey == null) return;

        // Todo revert to using actual data
        //DatabaseReference medDatabase = this.userDatabase.child(this.mAuth.getCurrentUser().getUid());
        DatabaseReference medDatabase = this.userDatabase.child("Gavin");

        medDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: snapshot getChildren returns" + snapshot.child(editMedKey));
                // Todo remove hardcode
                String medName = String.valueOf(snapshot.child(editMedKey).child("name").getValue());
                viewModel.setMedName(medName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getEditMedKey() {
        return this.editMedKey;
    }

    ;
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

}



/*
DatabaseReference timeDb = this.userDatabase.child(user.getUid()).child(Objects.requireNonNull(db.getKey())).child("time");
        timeDb.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Log.d(TAG, "_____doTransaction");
                return null;
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d(TAG, "_____onComplete: " + error);
            }
        });

        DatabaseReference doseDb = this.userDatabase.child(user.getUid()).child(Objects.requireNonNull(db.getKey())).child("dose");
        doseDb.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Log.d(TAG, "_____doTransaction");
                DoseTracker doseTracker = currentData.getValue(DoseTracker.class);
                if (doseTracker == null) {
                    doseTracker = new DoseTracker(null, null, null, null, null, null, null, null, null, null, null, null);
                }
                currentData.setValue(doseTracker);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d(TAG, "_____onComplete: " + error);
            }
        });
 */