package edu.northeastern.elderberry.addMed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<String> selectedItem = new MutableLiveData<>();
    private static final String TAG = "ItemViewModel";

    public void setItem(String item) {
        Log.d(TAG, "_____selectItem");
        this.selectedItem.setValue(item);
    }

    public LiveData<String> getSelectedItemArray() {
        Log.d(TAG, "_____getSelectedItem");
        return this.selectedItem;
    }

    @NonNull
    @Override
    public String toString() {
        Log.d(TAG, "_____toString");
        return "ItemViewModel{selectedItem=" + this.selectedItem + '}';
    }
}