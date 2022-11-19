package edu.northeastern.elderberry;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";
    private TextInputEditText forgotEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_forgot_password);

        Button sendEmailButton = findViewById(R.id.sendEmailButton);
        sendEmailButton.setOnClickListener(v -> {
            Log.d(TAG, "_____onClick");
            sendEmail();
        });

        this.forgotEmail = findViewById(R.id.forgotEmail);
    }

    private void sendEmail() {
        Log.d(TAG, "_____sendEmail");
        String email = Objects.requireNonNull(this.forgotEmail.getText()).toString();
        FirebaseAuth.getInstance().setLanguageCode("en");       // Set to English.
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            Log.d(TAG, "_____onComplete");
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password/email!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
            finish();
        });

    }
}