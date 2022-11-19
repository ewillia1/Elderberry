package edu.northeastern.elderberry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
        setContentView(R.layout.activity_main);

        // Calling this activity's function to use ActionBar utility methods.
        ActionBar actionBar = getSupportActionBar();

        // Providing a title fot the ActionBar.
        assert actionBar != null;
        actionBar.setTitle(R.string.app_name);

        // Providing a subtitle for the ActionBar.
        actionBar.setSubtitle(getString(R.string.medication_tracker));

        // Adding an icon in the ActionBar.
        actionBar.setIcon(R.mipmap.app_logo);

        // Methods to display the icon in the ActionBar.
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Button continueBt = findViewById(R.id.continueBt);
        continueBt.setOnClickListener(view -> startLoginActivity());
    }

    private void startLoginActivity() {
        Log.d(TAG, "_____startLoginActivity");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}