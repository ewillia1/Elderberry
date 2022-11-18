package edu.northeastern.elderberry;

import android.util.Log;

public class DisplayUsername {
    private static final String TAG = "DisplayUsername";
    private final String username;

    public String getUsername() {
        Log.d(TAG, "_____getUsername");
        return this.username;
    }

    public DisplayUsername(String username) {
        Log.d(TAG, "_____DisplayUsername");
        this.username = username;
    }
}
