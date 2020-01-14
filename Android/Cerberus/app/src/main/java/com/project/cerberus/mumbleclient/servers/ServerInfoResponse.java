package com.project.cerberus.mumbleclient.servers;

import com.project.cerberus.jumble.model.Server;
import java.nio.ByteBuffer;

public class ServerInfoResponse {
    private int mAllowedBandwidth;
    private int mCurrentUsers;
    private boolean mDummy;
    private long mIdentifier;
    private int mLatency;
    private int mMaximumUsers;
    private Server mServer;
    private int mVersion;

    public ServerInfoResponse(Server server, byte[] response, int latency) {
        this.mDummy = false;
        ByteBuffer buffer = ByteBuffer.wrap(response);
        this.mVersion = buffer.getInt();
        this.mIdentifier = buffer.getLong();
        this.mCurrentUsers = buffer.getInt();
        this.mMaximumUsers = buffer.getInt();
        this.mAllowedBandwidth = buffer.getInt();
        this.mLatency = latency;
        this.mServer = server;
    }

    public ServerInfoResponse() {
        this.mDummy = false;
        this.mDummy = true;
    }

    public long getIdentifier() {
        return this.mIdentifier;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public String getVersionString() {
        byte[] versionBytes = ByteBuffer.allocate(4).putInt(this.mVersion).array();
        return String.format("%d.%d.%d", new Object[]{Integer.valueOf(versionBytes[1]), Integer.valueOf(versionBytes[2]), Integer.valueOf(versionBytes[3])});
    }

    public int getCurrentUsers() {
        return this.mCurrentUsers;
    }

    public int getMaximumUsers() {
        return this.mMaximumUsers;
    }

    public int getAllowedBandwidth() {
        return this.mAllowedBandwidth;
    }

    public int getLatency() {
        return this.mLatency;
    }

    public Server getServer() {
        return this.mServer;
    }

    public boolean isDummy() {
        return this.mDummy;
    }
}
