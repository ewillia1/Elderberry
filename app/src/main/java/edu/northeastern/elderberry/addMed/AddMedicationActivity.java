package edu.northeastern.elderberry.addMed;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.northeastern.elderberry.Medicine;
import edu.northeastern.elderberry.R;

// TODO: Before adding to database, check to see all required fields are filled in.
// TODO: TextChangedListener in TimeDowViewHolder and thus doseWasAdded is be triggered/called when it is not supposed to.
public class AddMedicationActivity extends AppCompatActivity {

    private static final String TAG = "AddMedicationActivity";
    private DatabaseReference userDatabase;
    private FirebaseAuth mAuth;
    private ItemViewModel viewModel;
    private boolean medNameComplete;
    private boolean medInfoComplete;
    private boolean medFromDateComplete;
    private boolean medToDateComplete;
    private boolean medUnitComplete;
    private boolean allRequiredFieldsComplete;

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
                Toast.makeText(AddMedicationActivity.this, R.string.successful_add, Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "_____doAddDataToDb: db.getKey() = " + db.getKey());
        databaseReference.child(Objects.requireNonNull(db.getKey())).child("time").push().setValue(timeList);
        databaseReference.child(Objects.requireNonNull(db.getKey())).child("dose").push().setValue(doseList);
    }
}