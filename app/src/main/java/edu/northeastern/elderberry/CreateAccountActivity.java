package edu.northeastern.elderberry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth mAuth;
    private String firstName;
    private String lastName;
    private String registeredEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_create_account);

        // Initialize Firebase Auth.
        // Get the shared instance of the FirebaseAuth object.
        this.mAuth = FirebaseAuth.getInstance();

        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        // Providing a subtitle for the ActionBar.
        assert actionBar != null;
        actionBar.setSubtitle(Html.fromHtml("<small>" + getString(R.string.medication_tracker) + "</small>", Html.FROM_HTML_MODE_LEGACY));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Create Account Functionality.
        Button createAccountButton = findViewById(R.id.createAccount);
        createAccountButton.setOnClickListener(v -> {
            Log.d(TAG, "_____onCreate");
            registerUser();
        });
    }

    // When initializing the activity, check to see if the user is currently signed in.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private void registerUser() {
        Log.d(TAG, "_____registerUser");
        TextInputEditText ti_firstName = findViewById(R.id.ti_firstName);
        TextInputEditText ti_lastName = findViewById(R.id.ti_lastName);
        TextInputEditText tiRegisteredEmail = findViewById(R.id.ti_email);
        TextInputEditText tiRegisteredPassword = findViewById(R.id.ti_password);
        this.firstName = Objects.requireNonNull(ti_firstName.getText()).toString();
        this.lastName = Objects.requireNonNull(ti_lastName.getText()).toString();
        this.registeredEmail = Objects.requireNonNull(tiRegisteredEmail.getText()).toString();
        String registeredPassword = Objects.requireNonNull(tiRegisteredPassword.getText()).toString();

        if (isInvalid(this.firstName) || isInvalid(this.lastName) || isInvalid(this.registeredEmail) || isInvalid(registeredPassword)) {
            Toast.makeText(this, "Unsuccessful account creation! Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        this.mAuth.createUserWithEmailAndPassword(this.registeredEmail, registeredPassword)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "_____registerUser");
                    if (task.isSuccessful()) {
                        Log.d(TAG, "_____registerUser(success)");
                        User user = new User(firstName, lastName, registeredEmail);
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> showMedicationTrackerActivity());
                    } else {
                        Log.d(TAG, "_____registerUser(failure): " + Objects.requireNonNull(task.getException()).getMessage(), task.getException());
                        Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        Intent intent = new Intent(this, MedicationTrackerActivity.class);
        startActivity(intent);
        finish();
    }

    private static boolean isInvalid(String s) {
        Log.d(TAG, "_____isValid");
        return s == null || s.isBlank() || s.isEmpty();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}