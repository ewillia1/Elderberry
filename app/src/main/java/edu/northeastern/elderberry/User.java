package edu.northeastern.elderberry;

import android.util.Log;

// Class used in CreateAccountActivity.java.
public class User {
    private static final String TAG = "User";
    public String firstName;
    public String lastName;
    public String email;

    public User() {
        Log.d(TAG, "_____User");
    }

    public User(String firstName, String lastName, String email) {
        Log.d(TAG, "_____User");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}