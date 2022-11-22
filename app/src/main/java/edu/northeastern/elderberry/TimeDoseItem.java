package edu.northeastern.elderberry;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

// TODO: Finish
public class TimeDoseItem implements Parcelable {
    private static final String TAG = "LinkItem";
    private final String time;
    private final String dose;

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

    public TimeDoseItem(String time, String dose) {
        Log.d(TAG, "_____LinkItem constructor");
        this.time = time;
        this.dose = dose;
    }

    protected TimeDoseItem(Parcel in) {
        Log.d(TAG, "_____LinkItem: (Parcel in)");
        this.time = in.readString();
        this.dose = in.readString();
    }

    public String getTime() {
        return this.time;
    }

    public String getDose() {
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
        parcel.writeString(this.time);
        parcel.writeString(this.dose);
    }
}