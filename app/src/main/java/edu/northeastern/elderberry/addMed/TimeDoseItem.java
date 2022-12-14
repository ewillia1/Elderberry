package edu.northeastern.elderberry.addMed;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TimeDoseItem implements Parcelable {
    private static final String TAG = "TimeDoseItem";
    public static final Creator<TimeDoseItem> CREATOR = new Creator<>() {
        @Override
        public TimeDoseItem createFromParcel(Parcel in) {
            Log.d(TAG, "_____createFromParcel");
            return new TimeDoseItem(in);
        }

        @Override
        public TimeDoseItem[] newArray(int size) {
            Log.d(TAG, "_____newArray");
            return new TimeDoseItem[size];
        }
    };
    private final String time;
    private final String dose;
    private final String unit;
    private int itemNumber;

    // Used specifically in SetTimesFragment.java.
    public TimeDoseItem(int itemNumber) {
        this(itemNumber, null, null, null);
        Log.d(TAG, "_____TimeDoseItem (1 arg constructor)");
    }

    public TimeDoseItem(int itemNumber, String time, String dose, String unit) {
        Log.d(TAG, "_____LinkItem constructor");
        this.itemNumber = itemNumber;
        this.time = time;
        this.dose = dose;
        this.unit = unit;
    }

    protected TimeDoseItem(Parcel in) {
        Log.d(TAG, "_____LinkItem: (Parcel in)");
        this.itemNumber = in.readInt();
        this.time = in.readString();
        this.dose = in.readString();
        this.unit = in.readString();
    }

    public int getItemNumber() {
        Log.d(TAG, "_____getItemNumber");
        return this.itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        Log.d(TAG, "_____setItemNumber");
        this.itemNumber = itemNumber;
    }

    public String getTime() {
        Log.d(TAG, "_____getTime");
        return this.time;
    }

    public String getDose() {
        Log.d(TAG, "_____getDose");
        return this.dose;
    }

    @Override
    public int describeContents() {
        Log.d(TAG, "_____describeContents");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Log.d(TAG, "_____writeToParcel");
        parcel.writeInt(this.itemNumber);
        parcel.writeString(this.time);
        parcel.writeString(this.dose);
        parcel.writeString(this.unit);
    }
}