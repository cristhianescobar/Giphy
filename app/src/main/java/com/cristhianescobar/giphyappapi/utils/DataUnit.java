package com.cristhianescobar.giphyappapi.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public class DataUnit implements Parcelable {
        public int iconId;
        public String title;
        public String urlImage;

    @NonNull
    public static DataUnit getDataUnit(String name) {
        DataUnit d = new DataUnit();
        d.title = name;
        return d;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.iconId);
        dest.writeString(this.title);
        dest.writeString(this.urlImage);
    }

    public DataUnit() {
    }

    protected DataUnit(Parcel in) {
        this.iconId = in.readInt();
        this.title = in.readString();
        this.urlImage = in.readString();
    }

    public static final Creator<DataUnit> CREATOR = new Creator<DataUnit>() {
        public DataUnit createFromParcel(Parcel source) {
            return new DataUnit(source);
        }

        public DataUnit[] newArray(int size) {
            return new DataUnit[size];
        }
    };
}
