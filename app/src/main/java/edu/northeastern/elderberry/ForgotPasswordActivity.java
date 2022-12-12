package edu.northeastern.elderberry;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";
    private TextInputEditText forgotEmail;

    private static boolean isInvalid(String s) {
        Log.d(TAG, "_____isValid");
        return s == null || s.isBlank() || s.isEmpty();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_forgot_password);

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

        Button sendEmailButton = findViewById(R.id.sendEmailButton);
        sendEmailButton.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick");
            sendEmail();
        });

        this.forgotEmail = findViewById(R.id.forgotEmail);
    }

    private void sendEmail() {
        Log.d(TAG, "_____sendEmail");

        if (this.forgotEmail.getText() == null || isInvalid(this.forgotEmail.getText().toString())) {
            Toast.makeText(this, "Unsuccessful password reset! Please enter a valid email.", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = Objects.requireNonNull(this.forgotEmail.getText()).toString();
        FirebaseAuth.getInstance().setLanguageCode("en");       // Set to English.
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            Log.d(TAG, "_____onComplete");
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password/email!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            finish();
        });
    }
}