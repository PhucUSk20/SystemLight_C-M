package com.project.SLCAM;

import android.os.Parcel;
import android.os.Parcelable;

public class TrafficLight implements Parcelable {
    private String name;

    public TrafficLight(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrafficLight(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrafficLight> CREATOR = new Creator<TrafficLight>() {
        @Override
        public TrafficLight createFromParcel(Parcel in) {
            return new TrafficLight(in);
        }

        @Override
        public TrafficLight[] newArray(int size) {
            return new TrafficLight[size];
        }
    };
}
