package edu.northeastern.elderberry.dayview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.elderberry.MedicineDoseTime;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.addMed.AddMedicationActivity;
import edu.northeastern.elderberry.your_medication.YourMedicationsActivity;

// TODO: CHECKBOX functionality #2
// When opening a particular day the checkboxes have to be repopulated based on the saved taken data values (ex. true = checked, false = not checked).
// For each medicine (ParentItem) and each time for said medicine (ChildItem) this has to be the case.
// Not 100% sure how I will do that yet -- depends on what data structures I have access to and what data structures I need to create.
public class MedicationDayViewActivity extends AppCompatActivity {
    private static final String TAG = "MedicationDayViewActivity";
    public static final String MED_DAY_VIEW_KEY = "medDayViewKey";
    private final List<ParentItem> medicineList = new ArrayList<>();
    private final ArrayList<Boolean> takenList = new ArrayList<>();
    ParentItemAdapter parentItemAdapter;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_recycle_med_dayview);

        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        this.currentDate = getIntent().getStringExtra("current_date");

        // Providing a subtitle for the ActionBar.
        assert actionBar != null;
        actionBar.setSubtitle(Html.fromHtml("<small>" + getString(R.string.medication_tracker) + "</small>", Html.FROM_HTML_MODE_LEGACY));

        TextView medViewDate = findViewById(R.id.dayview_textView);
        medViewDate.setText(this.currentDate);

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

        // get correct db reference
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference medDatabase = userDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        Log.d(TAG, "onCreate: Retrieving user med db with user ID" + mAuth.getCurrentUser().getUid());

        medDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");

                medicineList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    List<ChildItem> children = new ArrayList<>();
                    Log.d(TAG, "_____onDataChange: level 1 ");
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);

                    if ((medicineDoseTime != null ? medicineDoseTime.getTime() : null) == null) {
                        Log.d(TAG, "_____onDataChange: medicineDoseTime.getTime() == null");
                        continue;
                    }

                    Log.d(TAG, "_____onDataChange: medicineDoseTime name " + medicineDoseTime.getName());

                    try {
                        if (!isCurrentDate(medicineDoseTime)) {
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.d(TAG, "_____onDataChange: parse datetime format is not aligned with the passed datetime");
                    }

                    Log.d(TAG, "_____onDataChange: medicineDoseTime.getTime().entrySet() = " + medicineDoseTime.getTime().entrySet());
                    for (Map.Entry<String, List<String>> entry : medicineDoseTime.getTime().entrySet()) {
                        // there is only one key in the hashmap
                        Log.d(TAG, "_____onDataChange: level 2 ");
                        for (String t : entry.getValue()) {
                            ChildItem fd = new ChildItem(t);
                            children.add(fd);
                        }
                        Log.d(TAG, "_____onDataChange: level 3 retrieve correct medicineDoseTime successfully ");
                    }

                    medicineList.add(new ParentItem(medicineDoseTime.getName(), children));
                }

                parentItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "_____onCancelled");
            }
        });

        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        ParentRecyclerViewItem.setHasFixedSize(true);
        ParentRecyclerViewItem.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.parentItemAdapter = new ParentItemAdapter(this.medicineList, this);
        ParentRecyclerViewItem.setAdapter(this.parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    private boolean isCurrentDate(MedicineDoseTime medicineDoseTime) throws ParseException {
        Log.d(TAG, "_____isCurrentDate");
        Date fromDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(medicineDoseTime.getFromDate());
        Date toDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(medicineDoseTime.getToDate());
        if (currentDate == null) {
            currentDate = medicineDoseTime.getFromDate();
        }

        Date selectedDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(currentDate);
        assert fromDate != null;
        assert toDate != null;
        assert selectedDate != null;
        return fromDate.compareTo(selectedDate) <= 0 && selectedDate.compareTo(toDate) <= 0;
    }

    private void startMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        finish();
    }

    private void startAddMedicationActivity() {
        Log.d(TAG, "_____startAddMedicationActivity");
        Intent intent = new Intent(this, AddMedicationActivity.class);
        intent.putExtra(MED_DAY_VIEW_KEY, true);
        startActivity(intent);

        // Want to finish this activity so that when add or edit is pressed onCreate for this activity is called again.
        // So nothing is null.
        finish();
    }

    private void startYourMedicationsActivity() {
        Log.d(TAG, "_____startYourMedicationsActivity");
        Intent intent = new Intent(this, YourMedicationsActivity.class);
        startActivity(intent);
    }

    // child_item.xml android:onClick for checkbox. Called when the checkbox is clicked (or un-clicked).
    // Need to set database according to which one the user did.
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        Log.d(TAG, "_____onCheckboxClicked: checked = " + checked);

        if (view.getId() == R.id.checkbox_child_item) {
            checkboxConfig(checked);
        }
    }

    // Helper method. Figures out the index the checkbox corresponds to in the taken array in the database.
    private void checkboxConfig(boolean checked) {
        Log.d(TAG, "_____checkboxConfig");
        // Set the index in the taken array in the database to false.

        // TODO: CHECKBOX functionality #1
        // * Access the time frequency node in the database and set that value to timeFreq
        // * Figure out what day number in the range the user clicked on (ex. Range = Dec 1 - 10. User clicked on Dec 5, day = 4) (index starting at 0)
        // * Figure out what ChildItem checkbox the usr clicked on for particular medicine -- set that to be checkBoxNum (index starting at 0)

        // Variables set to 0 need to be set to the above notes accordingly.
        int timeFreq = 0;
        int day = 0;
        int firstIndexForDay = timeFreq * day;
        int checkBoxNum = 0;
        int index = firstIndexForDay + checkBoxNum;

        setCheckbox(checked, index);
    }

    // Helper method. Set the index of the taken array to be the value of checked.
    // Meaning -- If the checkbox was checked, set the index in the taken array in the database to true.
    // Else, set the index in the taken array in the database to false.
    private void setCheckbox(boolean checked, int index) {
        Log.d(TAG, "_____setCheckbox");
        // TODO: CHECKBOX functionality #1
        // * Access particular medication
        // * Access taken array (get taken ID key)
        // * Set the index of that taken array to be the value of checked
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}