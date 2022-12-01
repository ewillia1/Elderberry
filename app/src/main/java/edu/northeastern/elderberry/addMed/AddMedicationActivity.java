package edu.northeastern.elderberry.addMed;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Objects;

import edu.northeastern.elderberry.Dose;
import edu.northeastern.elderberry.Medicine;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.Time;

// TODO: Add database functionality and check to see all required fields are filled in.
// TODO: Make fields required.
// TODO: Save from and to dates in database.
// TODO: Get the add button to work.
// TODO: Get focus to change when keyboard is collapsed.
// TODO: How to get time picker to show hour first and not minute (happens at time 7).
// TODO: Click enter in time recycler.
public class AddMedicationActivity extends AppCompatActivity {

    private static final String TAG = "AddMedicationActivity";
    private DatabaseReference userDatabase;
    private FirebaseAuth mAuth;
    private ItemViewModel viewModel;

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
                Toast.makeText(AddMedicationActivity.this, R.string.successful_add, Toast.LENGTH_SHORT).show();
                if (completeFieldsFilled()) {
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
        this.viewModel.getMedName().observe(this, item -> Log.d(TAG, "____onCreate: med name entered = " + item));
        this.viewModel.getInformation().observe(this, item -> Log.d(TAG, "____onCreate: information entered = " + item));
        this.viewModel.getFromDate().observe(this, item -> Log.d(TAG, "onCreate: from date entered = " + item));
        this.viewModel.getToDate().observe(this, item -> Log.d(TAG, "onCreate: to date entered = " + item));
        for (int i = 0; i < 12; i++) {
            int finalI = i;
            this.viewModel.getTime(i).observe(this, item -> Log.d(TAG, "onCreate: to time " + (finalI + 1) + " entered = " + item));
            this.viewModel.getDose(i).observe(this, item -> Log.d(TAG, "onCreate: to dose " + (finalI + 1) + " entered = " + item));
        }
    }

    private void doAddDataToDb() {
        FirebaseUser user = this.mAuth.getCurrentUser();
        assert user != null;
        Log.d(TAG, "_____doAddDataToDb: user.getUid() = " + user.getUid());
        DatabaseReference databaseReference = this.userDatabase.child(user.getUid());
        databaseReference.push().setValue(new Medicine(this.viewModel.getMedName().getValue(),
                this.viewModel.getInformation().getValue(),
                this.viewModel.getFromDate().getValue(),
                this.viewModel.getToDate().getValue(),
                this.viewModel.getUnit().getValue()));

//        Query lastQuery = databaseReference.child("mp").orderByKey().limitToLast(1);

        for (int i = 0; i < 12; i++) {
            databaseReference.child("time").push().setValue(new Time(this.viewModel.getTime(i).getValue()));
        }

        for (int j = 0; j < 12; j++) {
            databaseReference.child("dose").push().setValue(new Dose(this.viewModel.getDose(j).getValue()));
        }
    }

    // TODO: finish.
    private boolean completeFieldsFilled() {

        return true;
    }
}