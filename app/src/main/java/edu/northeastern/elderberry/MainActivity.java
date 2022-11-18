package edu.northeastern.elderberry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
}