package edu.northeastern.elderberry.addMed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ItemViewModel extends ViewModel {
    private static final String TAG = "ItemViewModel";
    private static final int MAX_INDEX = 12;
    private final MutableLiveData<String> medId = new MutableLiveData<>();
    private final MutableLiveData<String> timeId = new MutableLiveData<>();
    private final MutableLiveData<String> doseId = new MutableLiveData<>();
    private final MutableLiveData<String> takenId = new MutableLiveData<>();
    private final MutableLiveData<String> medName = new MutableLiveData<>();
    private final MutableLiveData<String> information = new MutableLiveData<>();
    private final MutableLiveData<String> fromDate = new MutableLiveData<>();
    private final MutableLiveData<String> toDate = new MutableLiveData<>();
    private final MutableLiveData<String> timeFreq = new MutableLiveData<>();
    private final MutableLiveData<String> unit = new MutableLiveData<>();
    private ArrayList<MutableLiveData<String>> time = initializeArray();
    private ArrayList<MutableLiveData<String>> dose = initializeArray();
    private ArrayList<MutableLiveData<Boolean>> taken;

    private ArrayList<MutableLiveData<String>> initializeArray() {
        ArrayList<MutableLiveData<String>> res = new ArrayList<>();
        Log.d(TAG, "_____initializeArray");
        for (int i = 0; i < MAX_INDEX; i++) {
            res.add(i, new MutableLiveData<>());
        }
        return res;
    }

    public void initializeTakenBooleanArray() {
        if (getTimeFreq().getValue() != null) {
            ArrayList<MutableLiveData<Boolean>> res = new ArrayList<>();
            long arraySize = Integer.parseInt(getTimeFreq().getValue()) * computeNumDays();
            Log.d(TAG, "_____initializeTimeArray with size " + arraySize);
            for (int i = 0; i < arraySize; i++) {
                res.add(i, new MutableLiveData<>(false));
            }
            this.taken = res;
        }
    }

    public void reinitializeTimeAndDoseArray() {
        Log.d(TAG, "_____reinitializeTimeAndDoseArray");
        this.time = initializeArray();
        this.dose = initializeArray();
    }

    public ArrayList<String> getTimeStringArray() {
        Log.d(TAG, "_____getTimeStringArray");
        ArrayList<String> timeStringArray = new ArrayList<>();
        int upperBound = this.time.size();
        for (int i = 0; i < upperBound; i++) {
            if (this.time.get(i).getValue() == null) {
                break;
            }
            timeStringArray.add(this.time.get(i).getValue());
        }
        return timeStringArray;
    }

    public ArrayList<Boolean> getTakenBooleanArray() throws NullPointerException {
        Log.d(TAG, "_____getTakenBooleanArray" + this.taken);
        if (this.taken == null) {
            throw new NullPointerException("field taken is not initialized. Call set From To date and call InitializeBoolean Array");
        }

        ArrayList<Boolean> takenBooleanArray = new ArrayList<>();
        int upperBound = this.taken.size();
        for (int i = 0; i < upperBound; i++) {
            if (this.taken.get(i).getValue() == null) {
                break;
            }
            takenBooleanArray.add(this.taken.get(i).getValue());
        }
        return takenBooleanArray;
    }

    public ArrayList<String> getDoseStringArray() {
        Log.d(TAG, "_____getDoseStringArray");
        ArrayList<String> doseStringArray = new ArrayList<>();
        int upperBound = this.dose.size();
        for (int i = 0; i < upperBound; i++) {
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
        this.dose.set(index, new MutableLiveData<>(item));
    }

    public void setTime(List<String> timeList) {
        this.time = initializeArray();
        Log.d(TAG, "_____setTime array version");
        int upperBound = Math.min(timeList.size(), this.time.size());
        for (int i = 0; i < upperBound; i++) {
            this.time.set(i, new MutableLiveData<>(timeList.get(i)));
        }
    }

    public void setDose(List<String> doseList) {
        this.dose = initializeArray();
        Log.d(TAG, "_____setDose array version");
        int upperBound = Math.min(doseList.size(), this.dose.size());
        for (int i = 0; i < upperBound; i++) {
            this.dose.set(i, new MutableLiveData<>(doseList.get(i)));
        }
    }

    public void setTaken(List<Boolean> takenList) {
        Log.d(TAG, "_____setTaken");
        initializeTakenBooleanArray();
        int upperBound = Math.min(takenList.size(), this.taken.size());
        for (int i = 0; i < upperBound; i++) {
            this.taken.set(i, new MutableLiveData<>(takenList.get(i)));
        }
    }

    public void setTimeFreq(String item) {
        Log.d(TAG, "_____setTimeFreq");
        this.timeFreq.setValue(item);
    }

    public MutableLiveData<String> getMedId() {
        Log.d(TAG, "_____getMedId");
        return this.medId;
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
        Log.d(TAG, "_____getTimeFreq");
        return this.timeFreq;
    }

    public int inferTimeFreq() {
        int count = 0;
        int upperBound = time.size();
        for (int i = 0; i < upperBound; i++) {
            if (time.get(i).getValue() != null) count++;
        }
        return count;
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

    public MutableLiveData<String> getTimeId() {
        return this.timeId;
    }

    public void setTimeId(String item) {
        Log.d(TAG, "_____setToDate");
        this.timeId.setValue(item);
    }

    public MutableLiveData<String> getDoseId() {
        return this.doseId;
    }

    public void setDoseId(String item) {
        Log.d(TAG, "_____setToDate");
        this.doseId.setValue(item);
    }

    public MutableLiveData<String> getTakenId() {
        return this.takenId;
    }

    public void setTakenId(String item) {
        Log.d(TAG, "_____setToDate");
        this.takenId.setValue(item);
    }

    private long computeNumDays() {
        Log.d(TAG, "_____computeNumDays: ");
        try {
            if (getFromDate().getValue() == null || getToDate().getValue() == null) {
                return 1;
            }

            Date fromDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(Objects.requireNonNull(getFromDate().getValue()));
            Date toDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(Objects.requireNonNull(getToDate().getValue()));

            assert toDate != null;
            assert fromDate != null;
            long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            Log.d(TAG, "_____computeNumDays successful, num days btw from & to is " + diff);
            return diff + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1;
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