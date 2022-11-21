package edu.northeastern.elderberry;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        setContentView(R.layout.activity_medication_dayview);
        // setting up db
        this.userDB = FirebaseDatabase.getInstance().getReference();

        // setting up cardView
        cardView = findViewById(R.id.base_cardview);
        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);

        arrow.setOnClickListener(view -> {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (hiddenView.getVisibility() == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
            }

            // If the CardView is not expanded, set its visibility to
            // visible and change the expand more icon to expand less.
            else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
            }
        });

        String user = "Gavin";
        this.userDB.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                medicines.add(snapshot.getValue(Medicine.class).getName());
                Log.d(TAG, "onDataChange: " + "med is " + medicines.get(0));
                // for (DataSnapshot d: snapshot.getChildren()) {
                //    //medicines.add();
                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}