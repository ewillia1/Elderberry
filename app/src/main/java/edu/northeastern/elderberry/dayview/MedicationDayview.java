package edu.northeastern.elderberry.dayview;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.elderberry.Medicine;
import edu.northeastern.elderberry.R;

public class MedicationDayview extends AppCompatActivity {
    private static final String TAG = "MedicationDayViewActivity";

    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    private DatabaseReference userDB;
    private List<String> medicines = new ArrayList<>();

    // Todo show the data of the medication
    // Todo add database reference
    // Todo add checkbox to the collapsable card view
    // Todo checkbox should result in data updated somewhere
    // Todo get user id to this view for db query

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_medication_dayview);
        setContentView(R.layout.activity_recycle_med_dayview);
        // setting up db
        this.userDB = FirebaseDatabase.getInstance().getReference();
        RecyclerView
                ParentRecyclerViewItem
                = findViewById(
                R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                MedicationDayview .this);

        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
        ParentItemAdapter
                parentItemAdapter
                = new ParentItemAdapter(
                ParentItemList());

        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview
        ParentRecyclerViewItem
                .setAdapter(parentItemAdapter);
        ParentRecyclerViewItem
                .setLayoutManager(layoutManager);

        // Todo to provide the correct username based on log-in info
        this.userDB.child("Gavin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //medicines.add(snapshot.getValue(Medicine.class).getName());
                //Medicine md = snapshot.getValue(Medicine.class);
                //Log.d(TAG, "_____onDataChange: medicine is " + md.getName());
                 for (DataSnapshot d: snapshot.getChildren()) {
                     //Medicine md = d.getValue(Medicine.class);

                     //Log.d(TAG, "onDataChange: " + "med is " + md.getName());
                    //medicines.add();
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<ParentItem> ParentItemList()
    {
        List<ParentItem> itemList
                = new ArrayList<>();

        ParentItem item
                = new ParentItem(
                "Title 1",
                ChildItemList());
        itemList.add(item);
        ParentItem item1
                = new ParentItem(
                "Title 2",
                ChildItemList());
        itemList.add(item1);
        ParentItem item2
                = new ParentItem(
                "Title 3",
                ChildItemList());
        itemList.add(item2);
        ParentItem item3
                = new ParentItem(
                "Title 4",
                ChildItemList());
        itemList.add(item3);

        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<ChildItem> ChildItemList()
    {
        List<ChildItem> ChildItemList
                = new ArrayList<>();

        ChildItemList.add(new ChildItem("Card 1"));
        ChildItemList.add(new ChildItem("Card 2"));
        ChildItemList.add(new ChildItem("Card 3"));
        ChildItemList.add(new ChildItem("Card 4"));

        return ChildItemList;

        // setting up cardView
        //cardView = findViewById(R.id.base_cardview);
        //arrow = findViewById(R.id.arrow_button);
        //hiddenView = findViewById(R.id.hidden_view);

        //arrow.setOnClickListener(view -> {
        //    // If the CardView is already expanded, set its visibility
        //    // to gone and change the expand less icon to expand more.
        //    if (hiddenView.getVisibility() == View.VISIBLE) {
        //        // The transition of the hiddenView is carried out by the TransitionManager class.
        //        // Here we use an object of the AutoTransition Class to create a default transition
        //        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
        //        hiddenView.setVisibility(View.GONE);
        //        arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
        //    }

        //    // If the CardView is not expanded, set its visibility to
        //    // visible and change the expand more icon to expand less.
        //    else {
        //        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
        //        hiddenView.setVisibility(View.VISIBLE);
        //    }
        //});


    }
}