package com.project.cerberus.jumble.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelableByteArray implements Parcelable {
    public static final Creator<ParcelableByteArray> CREATOR = new C02161();
    private byte[] mByteArray;

    /* renamed from: com.project.cerberus.jumble.util.ParcelableByteArray$1 */
    static class C02161 implements Creator<ParcelableByteArray> {
        C02161() {
        }

        public ParcelableByteArray createFromParcel(Parcel source) {
            return new ParcelableByteArray(source);
        }

        public ParcelableByteArray[] newArray(int size) {
            return new ParcelableByteArray[size];
        }
    }

    public ParcelableByteArray(byte[] array) {
        this.mByteArray = array;
    }

    private ParcelableByteArray(Parcel in) {
        this.mByteArray = new byte[in.readInt()];
        in.readByteArray(this.mByteArray);
    }

    public byte[] getBytes() {
        return this.mByteArray;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mByteArray.length);
        dest.writeByteArray(this.mByteArray);
    }
}
