package edu.northeastern.elderberry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ForgotEmailActivity extends AppCompatActivity {
    private static final String TAG = "ForgotEmailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_forgot_email);
    }
}