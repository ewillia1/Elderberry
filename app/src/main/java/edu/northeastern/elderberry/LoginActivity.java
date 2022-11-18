package edu.northeastern.elderberry;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private String username;
    private String password;
    private DatabaseReference userDatabase;
    private final ArrayList<DisplayUsername> listOfUsers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_login);

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

        TextView createAccount = findViewById(R.id.createAccount);
        createAccount.setOnClickListener(view -> openCreateAccount());

        // Temporary
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick");
            startMedicationTrackerActivity();
        });

        // Init the database
        this.userDatabase = FirebaseDatabase.getInstance().getReference("users");

        this.userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "_____onDataChange");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    listOfUsers.add(new DisplayUsername((String) dataSnapshot.child("username").getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "_____onCancelled");
                Toast.makeText(LoginActivity.this, "Error: Unable to get access to Database", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void startMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        Intent intent = new Intent(this, MedicationTrackerActivity.class);
        startActivity(intent);
    }

    private void openCreateAccount() {
        Log.d(TAG, "_____openCreateAccount");
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_user);
        EditText enterUsername = dialog.findViewById(R.id.enterUsername);
        EditText enterPassword = dialog.findViewById(R.id.enterPassword);
        Button createButton = dialog.findViewById(R.id.createAccount);

        createButton.setOnClickListener(view -> {
            this.username = enterUsername.getText().toString();
            this.password = enterPassword.getText().toString();


            if (isInvalid(this.username) || isInvalid(this.password)) {
                Snackbar.make(view, "Unsuccessful account creation!", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(view, "Successful account creation!", Snackbar.LENGTH_SHORT).show();
            }
            // createNewUser(view);
            dialog.dismiss();
        });

        dialog.show();

        // Adjust the size of the dialog.
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private static boolean isInvalid(String s) {
        Log.d(TAG, "_____isValid");
        return s == null || s.isBlank();
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Method to control the operations that will happen when user clicks on the action buttons.
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "_____onOptionsItemSelected");
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.about:
                Log.d(TAG, "_____onOptionsItemSelected (about)");
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                Log.d(TAG, "_____onOptionsItemSelected (settings)");
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void createNewUser(View view) {
        Log.d(TAG, "_____createNewUser");
        // Todo add password to the database
        // Todo check for duplication username adds
        EditText newUserName = findViewById(R.id.enterUsername);
        boolean duplicateUsername = false;
        Toast.makeText(LoginActivity.this,"CreateNewUser clicked", Toast.LENGTH_SHORT).show();

        if (newUserName.getText().toString().equals("") || newUserName.getText().toString().isBlank()) {
            Toast.makeText(LoginActivity.this, "Your username must include at least 1 non-white space character!", Toast.LENGTH_SHORT).show();
            return;
        }

        // check for duplicates in the database
        for (DisplayUsername username : listOfUsers) {
            if (username.getUsername().equals(newUserName.getText().toString())) {
                duplicateUsername = true;
                break;
            }
        }

        if (!duplicateUsername) {
            Map<String, String> newUser = new HashMap<>();
            newUser.put("username", newUserName.getText().toString());
            Task<Void> task = this.userDatabase.push().setValue(newUser);

            try {
                task.addOnSuccessListener(o -> finish());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Sorry! This username already exists. Please create a new one!", Toast.LENGTH_LONG).show();
        }
    }
}