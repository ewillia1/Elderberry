package edu.northeastern.elderberry.addMed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<String> medName = new MutableLiveData<>();
    private final MutableLiveData<String> information = new MutableLiveData<>();
    private final MutableLiveData<String> fromDate = new MutableLiveData<>();
    private final MutableLiveData<String> toDate = new MutableLiveData<>();
    private final MutableLiveData<String> unit = new MutableLiveData<>();
    private final MutableLiveData<String> time1 = new MutableLiveData<>();
    private final MutableLiveData<String> dose1 = new MutableLiveData<>();
    private static final String TAG = "ItemViewModel";
    //private final ArrayList<MutableLiveData<String>> time = new ArrayList<>();
    //private final ArrayList<MutableLiveData<String>> dose = new ArrayList<>();
    private final MutableLiveData<String> time[] = new MutableLiveData[12];
    private final MutableLiveData<String> dose[] = new MutableLiveData[12];

    public void initializeTimeArray() {
        for (int i = 0; i < 12; i++) {
            this.time[i] = new MutableLiveData<>();
        }
    }

    public void initializeDoseArray() {
        for (int i = 0; i < 12; i++) {
            this.dose[i] = new MutableLiveData<>();
        }
    }

    public void setMedName(String item) {
        Log.d(TAG, "_____setMedName");
        this.medName.setValue(item);
    }

    public void setInformation(String item) {
        Log.d(TAG, "_____setInformation");
        this.information.setValue(item);
    }

    public void setFromDate(String item) {
        Log.d(TAG, "_____setFromDate");
        this.fromDate.setValue(item);
    }

    public void setToDate(String item) {
        Log.d(TAG, "_____setToDate");
        this.toDate.setValue(item);
    }

    public void setUnit(String item) {
        Log.d(TAG, "_____setToDate");
        this.unit.setValue(item);
    }

    public void setTime1(String item) {
        Log.d(TAG, "_____setTime1");
        this.time1.setValue(item);
    }

    public void setTime(int index, String item) {
        Log.d(TAG, "_____setTime");
        this.time[index].setValue(item);
        Log.d(TAG, "_____setTime " + (this.time[index]));
    }

    public void setDose(int index, String item) {
        Log.d(TAG, "_____setDose");
        this.dose[index].setValue(item);
        Log.d(TAG, "_____setDose " + (this.dose[index]));
    }

    public void setDose1(String item) {
        Log.d(TAG, "_____setDose1");
        this.dose1.setValue(item);
    }

    public MutableLiveData<String> getMedName() {
        Log.d(TAG, "_____getMedName " + this.medName);
        return this.medName;
    }

    public MutableLiveData<String> getInformation() {
        Log.d(TAG, "_____getInformation");
        return this.information;
    }

    public MutableLiveData<String> getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return this.fromDate;
    }

    public MutableLiveData<String> getToDate() {
        Log.d(TAG, "_____getToDate");
        return this.toDate;
    }

    public MutableLiveData<String> getUnit() {
        Log.d(TAG, "_____getUnit");
        return this.unit;
    }

    public MutableLiveData<String> getTime1() {
        return this.time1;
    }

    public MutableLiveData<String> getDose1() {
        return this.dose1;
    }

    public MutableLiveData<String> getTime(int index) {
        Log.d(TAG, "_____getTime " + this.time[index]);
        return this.time[index];
    }

    public MutableLiveData<String> getDose(int index) {
        Log.d(TAG, "_____getDose " + this.dose[index]);
        return this.dose[index];
    }


    @NonNull
    @Override
    public String toString() {
        return "ItemViewModel{" +
                "medName=" + this.medName +
                ", information=" + this.information +
                ", fromDate=" + this.fromDate +
                ", toDate=" + this.toDate +
                ", unit=" + this.unit +
                ", time1=" + this.time1 +
                ", dose1=" + this.dose1 +
                '}';
    }
}