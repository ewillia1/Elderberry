package edu.northeastern.elderberry.addMed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ItemViewModel extends ViewModel {
    private static final String TAG = "ItemViewModel";
    private static final int MAX_INDEX = 12;
    private final MutableLiveData<String> medName = new MutableLiveData<>();
    private final MutableLiveData<String> information = new MutableLiveData<>();
    private final MutableLiveData<String> fromDate = new MutableLiveData<>();
    private final MutableLiveData<String> toDate = new MutableLiveData<>();
    private final MutableLiveData<String> timeFreq = new MutableLiveData<>();
    private final MutableLiveData<String> unit = new MutableLiveData<>();
    private final ArrayList<MutableLiveData<String>> time = new ArrayList<>();
    private final ArrayList<MutableLiveData<String>> dose = new ArrayList<>();

    public void initializeTimeArray() {
        Log.d(TAG, "_____initializeTimeArray");
        for (int i = 0; i < MAX_INDEX; i++) {
            this.time.add(i, new MutableLiveData<>());
        }
    }

    public void initializeDoseArray() {
        Log.d(TAG, "_____initializeDoseArray");
        for (int i = 0; i < MAX_INDEX; i++) {
            this.dose.add(i, new MutableLiveData<>());
        }
    }

    public ArrayList<String> getTimeStringArray() {
        Log.d(TAG, "_____getTimeStringArray");
        ArrayList<String> timeStringArray = new ArrayList<>();
        for (int i = 0; i < MAX_INDEX; i++) {
            timeStringArray.add(this.time.get(i).getValue());
        }
        return timeStringArray;
    }

    public ArrayList<String> getDoseStringArray() {
        Log.d(TAG, "_____getDoseStringArray");
        ArrayList<String> doseStringArray = new ArrayList<>();
        for (int i = 0; i < MAX_INDEX; i++) {
            doseStringArray.add(this.dose.get(i).getValue());
        }
        return doseStringArray;
    }

    public void setTime(int index, String item) {
        Log.d(TAG, "_____setTime");
        this.time.add(index, new MutableLiveData<>(item));
        Log.d(TAG, "_____setTime: " + this.time.get(index));
    }

    public void setDose(int index, String item) {
        Log.d(TAG, "_____setDose");
        this.dose.add(index, new MutableLiveData<>(item));
        Log.d(TAG, "_____setDose: " + this.dose.get(index));
    }

    public MutableLiveData<String> getMedName() {
        Log.d(TAG, "_____getMedName: " + this.medName);
        return this.medName;
    }

    public void setMedName(String item) {
        Log.d(TAG, "_____setMedName");
        this.medName.setValue(item);
    }

    public MutableLiveData<String> getInformation() {
        Log.d(TAG, "_____getInformation: " + this.information);
        return this.information;
    }

    public void setInformation(String item) {
        Log.d(TAG, "_____setInformation");
        this.information.setValue(item);
    }

    public MutableLiveData<String> getFromDate() {
        Log.d(TAG, "_____getFromDate");
        return this.fromDate;
    }

    public void setFromDate(String item) {
        Log.d(TAG, "_____setFromDate");
        this.fromDate.setValue(item);
    }

    public MutableLiveData<String> getToDate() {
        Log.d(TAG, "_____getToDate");
        return this.toDate;
    }

    public void setToDate(String item) {
        Log.d(TAG, "_____setToDate");
        this.toDate.setValue(item);
    }

    public MutableLiveData<String> getTimeFreq() {
        return this.timeFreq;
    }

    public void setTimeFreq(String item) {
        Log.d(TAG, "_____setTimeFreq");
        this.timeFreq.setValue(item);
    }

    public MutableLiveData<String> getUnit() {
        Log.d(TAG, "_____getUnit");
        return this.unit;
    }

    public void setUnit(String item) {
        Log.d(TAG, "_____setUnit");
        this.unit.setValue(item);
    }

    public MutableLiveData<String> getTime(int index) {
        Log.d(TAG, "_____getTime: " + this.time.get(index));
        return this.time.get(index);
    }

    public MutableLiveData<String> getDose(int index) {
        Log.d(TAG, "_____getDose: " + this.dose.get(index));
        return this.dose.get(index);
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemViewModel{" +
                "medName=" + this.medName +
                ", information=" + this.information +
                ", fromDate=" + this.fromDate +
                ", toDate=" + this.toDate +
                ", timeFreq=" + this.timeFreq +
                ", unit=" + this.unit +
                ", time=" + this.time +
                ", dose=" + this.dose +
                '}';
    }
}