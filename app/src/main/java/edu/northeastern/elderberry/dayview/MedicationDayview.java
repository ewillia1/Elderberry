package edu.northeastern.elderberry.dayview;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.northeastern.elderberry.MedicineDoseTime;
import edu.northeastern.elderberry.R;
import edu.northeastern.elderberry.your_medication.MedicineRow;

// 1 Todo restrict each day view to only show for that particular day selected - Team
// 1 Todo the UI does not load on first attempt - Christopher
// 1 Todo for bottom navigation bar to work - Christopher
public class MedicationDayview extends AppCompatActivity {
    private static final String TAG = "MedicationDayViewActivity";
    private final List<ParentItem> medicineList = new ArrayList<>();
    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabase;
    private ArrayList<String> medKey = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_recycle_med_dayview);

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

        // Setting up db.
        //DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();

        this.mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference medDatabase = this.userDatabase.child(this.mAuth.getCurrentUser().getUid());
        Log.d(TAG, "onCreate: Retrieving user med db with user ID" + this.mAuth.getCurrentUser().getUid());

        medDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");

                medicineList.clear();

                // 2 Todo adapt taken to accommodate all from and to dates
                for (DataSnapshot d : snapshot.getChildren()) {
                    medKey.add(d.getKey());
                    List<ChildItem> children = new ArrayList<>();
                    //for (DataSnapshot td : d.getChildren()) {
                    MedicineDoseTime medicineDoseTime = d.getValue(MedicineDoseTime.class);
                    Log.d(TAG, "onDataChange: level 1 medDoseTime is "+medicineDoseTime.toString());
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
                Log.d(TAG, "onDataChange: level 4 retrieve correct medicineDoseTime successfully " + medicineList.toString());



                // MedicineDoseTime med = snapshot.child(editMedKey).getValue(MedicineDoseTime.class);

                // add time to take the medicine to list
                //medicineList.add(new ParentItem(String.valueOf(d.child("name").getValue()), children));

                // medAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(MedicationDayview.this);
        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(medicineList);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        if (view.getId() == R.id.checkbox_child_item) {
            if (checked) {
                setComplete();
                // update flag to true in db
            } else {
            }
            // update flag to false in db
            // setIncomplete();
        }
    }

    public void setComplete() {
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