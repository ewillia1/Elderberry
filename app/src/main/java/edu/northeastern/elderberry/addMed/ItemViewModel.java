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
        Log.d(TAG, "_____initializeTimeArray");
        for (int i = 0; i < MAX_INDEX; i++) {
            res.add(i, new MutableLiveData<>());
        }
        return res;
    }

    public void initializeBooleanArray() {
        ArrayList<MutableLiveData<Boolean>> res = new ArrayList<>();
        // Todo remove this dependency
        long arraySize = inferTimeFreq() * computeNumDays();
        Log.d(TAG, "_____initializeTimeArray with size " + arraySize);
        for (int i = 0; i < arraySize; i++) {
            res.add(i, new MutableLiveData<>(false));
        }
        this.taken = res;
    }

    public void clear() {
        this.time = initializeArray();
        this.dose = initializeArray();
        initializeBooleanArray();
    }

    public ArrayList<String> getTimeStringArray() {
        Log.d(TAG, "_____getTimeStringArray");
        ArrayList<String> timeStringArray = new ArrayList<>();
        for (int i = 0; i < this.time.size(); i++) {
            if (this.time.get(i).getValue() == null) {
                break;
            }
            timeStringArray.add(this.time.get(i).getValue());
        }
        return timeStringArray;
    }

    public ArrayList<Boolean> getTakenBooleanArray() {
        Log.d(TAG, "_____getTakenBooleanArray"+this.taken);
        ArrayList<Boolean> takenBooleanArray = new ArrayList<>();
        for (int i = 0; i < this.taken.size(); i++) {
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
        for (int i = 0; i < this.dose.size(); i++) {
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
    }

    public void setTime(List<String> timeList) {
        this.time = initializeArray();
        Log.d(TAG, "_____setTime array version");
        for (int i=0; i < Math.min(timeList.size(), this.time.size()); i++) {
            this.time.add(i, new MutableLiveData<>(timeList.get(i)));
        }
    }

    public void setDose(List<String> doseList) {
        this.dose = initializeArray();
        Log.d(TAG, "_____setDose array version");
        for (int i=0; i < Math.min(doseList.size(), this.dose.size()); i++) {
            this.dose.add(i, new MutableLiveData<>(doseList.get(i)));
        }
    }


    public void resetTaken() {
        for (int i = 0 ; i < MAX_INDEX ; i++) {
            if (i < Integer.parseInt(Objects.requireNonNull(this.timeFreq.getValue()))) {
                this.taken.add(i, new MutableLiveData<>(Boolean.FALSE));
            }
            else {
                this.taken.add(i, new MutableLiveData<>());
            }
        }
    }

    public void setTaken(List<Boolean> takenList) {
        initializeBooleanArray();
        Log.d(TAG, "_____setTaken");
        for (int i=0; i < Math.min(takenList.size(), this.taken.size()); i++) {
            this.taken.add(i, new MutableLiveData<>(takenList.get(i)));
        }
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
        if (this.timeFreq.getValue() == null) {
            setTimeFreq(Integer.toString(inferTimeFreq()));
        }
        return this.timeFreq;
    }

    public void setTimeFreq(String item) {
        Log.d(TAG, "_____setTimeFreq");
        this.timeFreq.setValue(item);
    }

    public int inferTimeFreq() {
        int count = 0;
        for (int i=0; i< time.size(); i++) {
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

    private long computeNumDays() {
        Log.d(TAG, "_____computeNumDays: ");
        try {
            Date fromDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(Objects.requireNonNull(getFromDate().getValue()));
            //Log.d(TAG, "_____isCurrentDate: after from date parsed");
            //Log.d(TAG, "_____isCurrentDate: before to date parsed");
            Date toDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(Objects.requireNonNull(getToDate().getValue()));

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