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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.elderberry.R;

public class MedicationDayview extends AppCompatActivity {
    private static final String TAG = "MedicationDayViewActivity";

    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    private final List<ParentItem> medicineList = new ArrayList<>();

    // Todo restrict each day view to only show for that particular day selected
    // Todo add app logo to top menu bar
    // Todo first click on calendar does only brings up an empty activity

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
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();

        // Todo to provide the correct username based on log-in info
        userDB.child("Gavin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange: ");

                medicineList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    // td stands for time data
                    List<ChildItem> children = new ArrayList<>();
                    for (DataSnapshot td : d.child("time").getChildren()) {
                        ChildItem fd = new ChildItem(String.valueOf(td.getValue()));
                        children.add(fd);
                    }
                    // add time to take the medicine to list
                    medicineList.add(new ParentItem(String.valueOf(d.child("name").getValue()), children));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(MedicationDayview.this);

        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
//        ParentItemAdapter
//                parentItemAdapter
//                = new ParentItemAdapter(
//                ParentItemList());

        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(medicineList);
        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

        //findViewById(R.id.parent_item_title);

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
        // Todo update completion flag in database
    }

    private List<ParentItem> ParentItemList() {
        List<ParentItem> itemList = new ArrayList<>();

        ParentItem item = new ParentItem("Title 1", ChildItemList());
        itemList.add(item);
        ParentItem item1 = new ParentItem("Title 2", ChildItemList());
        itemList.add(item1);
        ParentItem item2 = new ParentItem("Title 3", ChildItemList());
        itemList.add(item2);
        ParentItem item3 = new ParentItem("Title 4", ChildItemList());
        itemList.add(item3);

        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<ChildItem> ChildItemList() {
        List<ChildItem> ChildItemList = new ArrayList<>();

        ChildItemList.add(new ChildItem("Card 1"));
        ChildItemList.add(new ChildItem("Card 2"));
        ChildItemList.add(new ChildItem("Card 3"));
        ChildItemList.add(new ChildItem("Card 4"));

        return ChildItemList;

    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}