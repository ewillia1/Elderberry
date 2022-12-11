package edu.northeastern.elderberry.dayview;

import static edu.northeastern.elderberry.util.DatetimeFormat.makeStringDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.elderberry.MedicineDoseTime;
import edu.northeastern.elderberry.ParentItemClickListener;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.addMed.AddMedicationActivity;
import edu.northeastern.elderberry.util.DatetimeFormat;
import edu.northeastern.elderberry.your_medication.YourMedicationsActivity;

// Todo handle the case where taken, time, dose array size added not properly
public class MedicationDayViewActivity extends AppCompatActivity {
    public static final String MED_DAY_VIEW_KEY = "medDayViewKey";
    public static final String DATE_KEY = "date_key";
    private static final String TAG = "MedicationDayViewActivity";
    private final List<ParentItem> medicineList = new ArrayList<>();
    //private final List<List<Boolean>> takenTodayList = new ArrayList<>();
    private ArrayList<MedicineDoseTime> medDoseTimeList = new ArrayList<>();
    private ArrayList<String> medKeyList = new ArrayList<>();
    //private final ArrayList<Boolean> takenList = new ArrayList<Boolean>();
    //private final ArrayList<Boolean> takenList = new ArrayList<>();
    private ParentItemAdapter parentItemAdapter;
    private String currentDate;
    private DatabaseReference medDatabase;
    private int parentPos;
    private int childPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____IN ON CREATE!!!!");
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

        // Get correct db reference.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference();
        medDatabase = userDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        Log.d(TAG, "onCreate: Retrieving user med db with user ID" + mAuth.getCurrentUser().getUid());

        medDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");

                medicineList.clear();
                medDoseTimeList.clear();

                // Todo reference https://www.folkstalk.com/tech/nested-recyclerview-onclicklistener-with-examples/
                // To figure out which position has been clicked?

                // if (!reloadDb) return;

                for (DataSnapshot d : snapshot.getChildren()) {
                    List<ChildItem> children = new ArrayList<>();
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);

                    if ((medicineDoseTime != null ? medicineDoseTime.getTime() : null) == null) {
                        // Todo check dose & taken are not null
                        Log.d(TAG, "_____onDataChange: medicineDoseTime.getTime() == null");
                        if (Objects.requireNonNull(medicineDoseTime).getName() != null) {
                            Log.d(TAG, "onDataChange: the medicine with the null time is: " + medicineDoseTime.getName());
                        }
                        continue;
                    }

                    Log.d(TAG, "_____onDataChange: NO NULL POINTER EXCEPTION! YAY! medicineDoseTime name " + medicineDoseTime.getName());

                    try {
                        if (!isCurrentDate(medicineDoseTime)) {
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.d(TAG, "_____onDataChange: parse datetime format is not aligned with the passed datetime");
                    }

                    ArrayList<String> scheduledTime = new ArrayList<>();
                    // To retrieve all the time headers
                    for (Map.Entry<String, List<String>> entry : medicineDoseTime.getTime().entrySet()) {
                        // there is only one key in the hashmap
                        for (String t : entry.getValue()) {
                            scheduledTime.add(t);
                        }
                    }

                    // Slice the correct subset of booleans
                    List<Boolean> takenToday = getTakenToday(medicineDoseTime);

                    // initialize the childItme & add it to child
                    for (int i = 0; i < scheduledTime.size(); i++) {
                        ChildItem fd = new ChildItem(scheduledTime.get(i), takenToday.get(i));
                        Log.d(TAG, "_____onDataChange: childItem " + fd.toString());
                        children.add(fd);
                    }

                    // Done
                    medicineList.add(new ParentItem(medicineDoseTime.getName(), children));
                    medKeyList.add(d.getKey());
                    //takenTodayList.add(getTakenToday(medicineDoseTime));
                    // Todo: filter the taken array for rendering. subset of taken
                    medDoseTimeList.add(medicineDoseTime);
                    // New arrayList with
                }

                parentItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "_____onCancelled");
            }
        });

        // Recycler View.
        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        ParentRecyclerViewItem.setHasFixedSize(true);
        ParentRecyclerViewItem.setItemAnimator(new DefaultItemAnimator());

        // Set a listener for the parentItemAdapter.
        this.parentItemAdapter = new ParentItemAdapter(this.medicineList, (parentPosition, childPosition, isChecked) -> {
            Log.d(TAG, "_____parentItemClicked: parentPosition = " + parentPosition + ", childPosition = " + childPosition + ", isChecked = " + isChecked);
            parentPos = parentPosition;
            childPos = childPosition;
            Log.d(TAG, "_____onCreate: parentPos" + parentPos);
            Log.d(TAG, "_____onCreate: childPos" + childPos);
            checkboxConfig(isChecked);
        });

        ParentRecyclerViewItem.setAdapter(this.parentItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    private List<Boolean> getTakenToday(MedicineDoseTime med) {

        // Todo check bugs
        int dayOffset = DatetimeFormat.dateDiff(
                makeStringDate(med.getFromDate()),
                makeStringDate(currentDate));
        int freq = med.getFreq();
        int startIndex = dayOffset * freq;
        int endIndex = startIndex + freq;
        List<Boolean> takenVal = new ArrayList<>();
        for (Map.Entry<String, List<Boolean>> entry : med.getTaken().entrySet()) {
            takenVal = entry.getValue(); //
        }

        Log.d(TAG, "_____getTakenToday med name is " + med.getName());
        Log.d(TAG, "_____getTakenToday med fromDate is " + med.getFromDate());
        Log.d(TAG, "_____getTakenToday start index is " + startIndex + " endIndex is " + endIndex);
        Log.d(TAG, "_____getTakenToday taken array is  " + takenVal.toString());
        Log.d(TAG, "_____getTakenToday takenSubset is  " + takenVal.subList(startIndex, endIndex));

        return takenVal.subList(startIndex, endIndex);
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
        Log.d(TAG, "_____startAddMedicationActivity: about to start the add medication activity");
        Intent intent = new Intent(this, AddMedicationActivity.class);
        intent.putExtra(MED_DAY_VIEW_KEY, true);
        startActivity(intent);

        // Want to finish this activity so that when add or edit is pressed onCreate for this activity is called again.
        // So nothing is null.
        Log.d(TAG, "_____startAddMedicationActivity: about to finish this activity");
        finish();
    }

    private void startYourMedicationsActivity() {
        Log.d(TAG, "_____startYourMedicationsActivity");
        Intent intent = new Intent(this, YourMedicationsActivity.class);
        startActivity(intent);
    }

    // Helper method. Figures out the index the checkbox corresponds to in the taken array in the database.
    private void checkboxConfig(boolean checked) {
        Log.d(TAG, "_____checkboxConfig");
        MedicineDoseTime med = this.medDoseTimeList.get(this.parentPos);
        int timeFreq = med.getFreq();
        Log.d(TAG, "_____checkboxConfig med name is " + med.getName());
        int dayOffset = DatetimeFormat.dateDiff(
                makeStringDate(med.getFromDate()),
                makeStringDate(currentDate));

        Log.d(TAG, "_____checkboxConfig num of days from " + med.getFromDate() + " is " + dayOffset);

        int firstIndexForDay = timeFreq * dayOffset;
        int checkBoxNum = childPos;
        int index = firstIndexForDay + checkBoxNum;
        Log.d(TAG, "_____checkboxConfig: index = " + index);

        setCheckbox(checked, index);
    }

    // Helper method. Set the index of the taken array to be the value of checked.
    // Meaning -- If the checkbox was checked, set the index in the taken array in the database to true.
    // Else, set the index in the taken array in the database to false.
    private void setCheckbox(boolean checked, int index) {
        Log.d(TAG, "_____setCheckbox");
        MedicineDoseTime med = this.medDoseTimeList.get(this.parentPos);
        med.getTaken();
        String takenKey = "";
        List<Boolean> takenVal = new ArrayList<>();
        String medKey = medKeyList.get(this.parentPos);
        for (Map.Entry<String, List<Boolean>> entry : med.getTaken().entrySet()) {
            // There is only one key in the hashmap.
            takenKey = entry.getKey(); // what we want
            takenVal = entry.getValue();
            Log.d(TAG, "_____setCheckbox: takenKey is " + takenKey + " medkey at parentPos is " + medKey);
        }

        takenVal.set(index, checked);

        if (!takenKey.equals("")) {
            Log.d(TAG, "_____setCheckbox: takenKey is not empty and takenVal was updated to " + takenVal);
            Map<String, Object> taken = new HashMap<>();
            taken.put(takenKey, takenVal);
            Log.d(TAG, "_____setCheckbox index is " + index);
            Log.d(TAG, "_____setCheckbox medKey is " + medKey);

            medDatabase.child(medKey).child("taken").updateChildren(taken).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "_____setCheckbox: task.isSuccessful()");
                }
            });
        }
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}