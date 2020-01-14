package com.project.cerberus.jumble.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Server implements Parcelable {
    public static final Creator<Server> CREATOR = new C01931();
    private String mHost;
    private long mId;
    private String mName;
    private String mPassword;
    private int mPort;
    private String mUsername;

    /* renamed from: com.project.cerberus.jumble.model.Server$1 */
    static class C01931 implements Creator<Server> {
        C01931() {
        }

        public Server createFromParcel(Parcel parcel) {
            return new Server(parcel);
        }

        public Server[] newArray(int i) {
            return new Server[i];
        }
    }

    public Server(long id, String name, String host, int port, String username, String password) {
        this.mId = id;
        this.mName = name;
        this.mHost = host;
        this.mPort = port;
        this.mUsername = username;
        this.mPassword = password;
    }

    private Server(Parcel in) {
        readFromParcel(in);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        parcel.writeString(this.mName);
        parcel.writeString(this.mHost);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mUsername);
        parcel.writeString(this.mPassword);
    }

    public void readFromParcel(Parcel in) {
        this.mId = in.readLong();
        this.mName = in.readString();
        this.mHost = in.readString();
        this.mPort = in.readInt();
        this.mUsername = in.readString();
        this.mPassword = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public long getId() {
        return this.mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return (this.mName == null || this.mName.length() <= 0) ? this.mHost : this.mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getHost() {
        return this.mHost;
    }

    public void setHost(String mHost) {
        this.mHost = mHost;
    }

    public int getPort() {
        return this.mPort;
    }

    public void setPort(int mPort) {
        this.mPort = mPort;
    }

    public String getUsername() {
        return this.mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean isSaved() {
        return this.mId != -1;
    }
}
