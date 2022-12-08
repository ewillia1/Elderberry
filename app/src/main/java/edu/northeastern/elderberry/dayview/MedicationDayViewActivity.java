package edu.northeastern.elderberry.dayview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

// 1 Todo restrict each day view to only show for that particular day selected - Team
public class MedicationDayViewActivity extends AppCompatActivity {
    private static final String TAG = "MedicationDayViewActivity";
    private final List<ParentItem> medicineList = new ArrayList<>();
    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
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

        currentDate = getIntent().getStringExtra("current_date");

        // Providing a subtitle for the ActionBar.
        assert actionBar != null;
        actionBar.setSubtitle(Html.fromHtml("<small>" + getString(R.string.medication_tracker) + "</small>", Html.FROM_HTML_MODE_LEGACY));

        TextView medViewDate = findViewById(R.id.dayview_textView);
        medViewDate.setText(currentDate);

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
                    Log.d(TAG, "onDataChange: level 1 ");
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);
                    assert medicineDoseTime != null;
                    //1 Todo: make try-catch proper formatting
                    try {
                        if (!isCurrentDate(medicineDoseTime)) {
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (Map.Entry<String, List<String>> entry : medicineDoseTime.getTime().entrySet()) {
                        // there is only one key in the hashmap
                        Log.d(TAG, "onDataChange: level 2 ");
                        for (String t : entry.getValue()) {
                            ChildItem fd = new ChildItem(t);
                            children.add(fd);
                        }

                        Log.d(TAG, "onDataChange: level 3 retrieve correct medicineDoseTime successfully ");
                    }
                    medicineList.add(new ParentItem(medicineDoseTime.getName(), children));
                }
                parentItemAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        ParentRecyclerViewItem.setHasFixedSize(true);
        ParentRecyclerViewItem.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        parentItemAdapter = new ParentItemAdapter(medicineList);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

    }

    private boolean isCurrentDate(MedicineDoseTime medicineDoseTime) throws ParseException {
        Log.d(TAG, "_____isCurrentDate");
        Date fromDate=new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US).parse(medicineDoseTime.getFromDate());
        Date toDate=new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US).parse(medicineDoseTime.getToDate());
        if (currentDate == null) {
            currentDate = medicineDoseTime.getFromDate();
        }
        Date selectedDate=new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US).parse(currentDate);
        assert fromDate != null;
        assert toDate != null;
        assert selectedDate != null;
        Log.d(TAG, "isCurrentDate: " + fromDate + toDate + selectedDate);
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
                setComplete();
                // update flag to true in db
            } else {
                // TODO finish this code
            }
            // update flag to false in db
            // setIncomplete();
        }
    }

    public void setComplete() {
        Log.d(TAG, "_____setComplete");
        // 2 Todo the ability to check and uncheck the boolean value in the database
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}