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
import java.util.concurrent.BlockingDeque;

import edu.northeastern.elderberry.MedicineDoseTime;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.addMed.AddMedicationActivity;
import edu.northeastern.elderberry.your_medication.YourMedicationsActivity;

public class MedicationDayViewActivity extends AppCompatActivity {
    private static final String TAG = "MedicationDayViewActivity";
    private final List<ParentItem> medicineList = new ArrayList<>();
//    ImageButton arrow;
//    LinearLayout hiddenView;
//    CardView cardView;
    //    private final ArrayList<String> medKey = new ArrayList<>();
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

                // 2 Todo adapt taken to accommodate all from and to dates
                for (DataSnapshot d : snapshot.getChildren()) {
//                    medKey.add(d.getKey());
                    List<ChildItem> children = new ArrayList<>();
                    //for (DataSnapshot td : d.getChildren()) {
                    Log.d(TAG, "_____onDataChange: level 1 ");
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);
                    assert medicineDoseTime != null;
                    try {
                        if (!isCurrentDate(medicineDoseTime)) {
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onDataChange: parse datetime format is not aligned with the passed datetime");
                    }

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
        this.parentItemAdapter = new ParentItemAdapter(this.medicineList);
        ParentRecyclerViewItem.setAdapter(this.parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    private boolean isCurrentDate(MedicineDoseTime medicineDoseTime) throws ParseException {

        //Log.d(TAG, "_____isCurrentDate: before from date parsed");
        Date fromDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(medicineDoseTime.getFromDate());
        //Log.d(TAG, "_____isCurrentDate: after from date parsed");
        //Log.d(TAG, "_____isCurrentDate: before to date parsed");
        Date toDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(medicineDoseTime.getToDate());
        //Log.d(TAG, "_____isCurrentDate: after to date parsed");
        //Log.d(TAG, "_____isCurrentDate: before current date is null");
        if (currentDate == null) {
            currentDate = medicineDoseTime.getFromDate();
        }
        //Log.d(TAG, "_____isCurrentDate: after current date is null");
        //Log.d(TAG, "_____isCurrentDate: before selected date is parsed");
        Date selectedDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(currentDate);
        //Log.d(TAG, "_____isCurrentDate: after selected date is parsed");
        //Log.d(TAG, "_____isCurrentDate: fromDate" + fromDate.toString());
        //Log.d(TAG, "_____isCurrentDate: toDate"  + toDate.toString() );
        //Log.d(TAG, "_____isCurrentDate: selectedDate" + selectedDate.toString());
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
        startActivity(intent);
    }

    private void startYourMedicationsActivity() {
        Log.d(TAG, "_____startYourMedicationsActivity");
        Intent intent = new Intent(this, YourMedicationsActivity.class);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view) {
        Log.d(TAG, "_____onCheckboxClicked");
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        if (view.getId() == R.id.checkbox_child_item) {
            if (checked) {
                setChecked();
            } else {
                // TODO finish this code
                setUnhecked();
            }
        }
    }

    private void setChecked() {
        Log.d(TAG, "_____setComplete");
        // fromDate 1 Dec, 2022
        // to Date 31 Dec, 222
        // total of 31 days inclusive of both end
        // freq = 3
        // size of the array 31 * 3 = 93

        // pick 7 Dec, 2022, 2nd time you are taking the med
        // 0 index: retrieving the correct medicine
        // e.g. 1
        // 1st index is based off date
        /// 2nd index is based off position
        // 6 * 3 + 1 = 19

        // e.g. 2. 1 dec 2022, 1 st first frequency
        // 0 * 3 + 0 = 0
        // [false, false, false,  ... ,false] of size 93
        // 2 Todo the ability to check and uncheck the boolean value in the database
    }

    private void setUnhecked() {
        Log.d(TAG, "_____setUnhecked");
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}