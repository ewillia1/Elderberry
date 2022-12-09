package edu.northeastern.elderberry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import edu.northeastern.elderberry.helpAndConfigs.AboutActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static final String PREFS_NAME = "MyPrefsFile";
    private FirebaseAuth mAuth;
    private TextInputEditText email;
    private TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_login);

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

        TextView createAccount = findViewById(R.id.createAccount);
        createAccount.setOnClickListener(view -> openCreateAccount());

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (loginButton)");
            authenticateUser();
        });

        TextView forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick (forgotPassword)");
            resetPassword();
        });

        this.email = findViewById(R.id.loginEmail);
        this.password = findViewById(R.id.loginPassword);

        ImageButton emailMic = findViewById(R.id.emailMic);
        emailMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(true);
            }
        });

        ImageButton passwordMic = findViewById(R.id.passwordMic);
        passwordMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(false);
            }
        });
    }

    private void speak(boolean isForEmail) {
        Log.d(TAG, "_____speak");

        // Intent to show speech to text dialog.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (isForEmail) {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak you email");
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak you password");
        }

        // Start intent.
        try {
            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<String> answer = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if (isForEmail) {
                            email.setText(answer.get(0));
                        } else {
                            password.setText(answer.get(0));
                        }
                    }
                }
            });
        } catch (Exception e) {
            // If there is some error post a message of the error for the user to see.
            Log.d(TAG, "_____speak: " + e.getMessage());
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword() {
        Log.d(TAG, "_____resetPassword");
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openCreateAccount() {
        Log.d(TAG, "_____openCreateAccount");
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
        finish();
    }

    private void authenticateUser() {
        Log.d(TAG, "_____authenticateUser");
        TextInputEditText loginEmail = findViewById(R.id.loginEmail);
        TextInputEditText loginPassword = findViewById(R.id.loginPassword);

        String email = Objects.requireNonNull(loginEmail.getText()).toString();
        String password = Objects.requireNonNull(loginPassword.getText()).toString();

        if (email.isEmpty() || email.isBlank() || password.isEmpty() || password.isBlank()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "_____authenticateUser (signInWithEmail:success)");

                // Letting the program know that this user has officially logged in.
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn", true);
                editor.apply();

                showMedicationTrackerActivity();
            } else {
                // If sign in fails, display a message to the user.
                Log.d(TAG, "_____authenticateUser (signInWithEmail:failure): " + Objects.requireNonNull(task.getException()).getMessage(), task.getException());
                Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // When initializing the activity, check to see if the user is currently signed in.
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "_____onStart");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private void showMedicationTrackerActivity() {
        Log.d(TAG, "_____startMedicationTrackerActivity");
        Intent intent = new Intent(this, MedicationTrackerActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to inflate the options menu when the user opens the menu for the first time.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "_____onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Method to control the operations that will happen when user clicks on the action buttons.
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "_____onOptionsItemSelected");
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.about) {
            Log.d(TAG, "_____onOptionsItemSelected (about)");
            intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}