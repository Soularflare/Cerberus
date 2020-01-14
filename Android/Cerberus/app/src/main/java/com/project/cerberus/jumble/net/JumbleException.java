package com.project.cerberus.jumble.net;

import com.project.cerberus.jumble.protobuf.Mumble;
import com.project.cerberus.jumble.protobuf.Mumble.Reject;
import com.project.cerberus.jumble.protobuf.Mumble.UserRemove;

public class JumbleException extends Exception {
    private boolean mAutoReconnect;
    private JumbleDisconnectReason mReason;
    private Reject mReject;
    private UserRemove mUserRemove;

    public enum JumbleDisconnectReason {
        REJECT,
        USER_REMOVE,
        OTHER
    }

    public JumbleException(String message, Throwable e, boolean autoReconnect) {
        super(message, e);
        this.mReason = JumbleDisconnectReason.OTHER;
        this.mAutoReconnect = autoReconnect;
    }

    public JumbleException(String message, boolean autoReconnect) {
        super(message);
        this.mReason = JumbleDisconnectReason.OTHER;
        this.mAutoReconnect = autoReconnect;
    }

    public JumbleException(Throwable e, boolean autoReconnect) {
        super(e);
        this.mReason = JumbleDisconnectReason.OTHER;
        this.mAutoReconnect = autoReconnect;
    }

    public JumbleException(Reject reject) {
        super("Reject: " + reject.getReason());
        this.mReason = JumbleDisconnectReason.OTHER;
        this.mReject = reject;
        this.mReason = JumbleDisconnectReason.REJECT;
        this.mAutoReconnect = false;
    }

    public JumbleException(Mumble.UserRemove userRemove) {
        super((userRemove.getBan() ? "Banned: " : "Kicked: ")+userRemove.getReason());
        mUserRemove = userRemove;
        mReason = JumbleDisconnectReason.USER_REMOVE;
    }

    public JumbleDisconnectReason getReason() {
        return this.mReason;
    }

    public boolean isAutoReconnectAllowed() {
        return this.mAutoReconnect;
    }

    public void setAutoReconnectAllowed(boolean autoReconnect) {
        this.mAutoReconnect = autoReconnect;
    }

    public Reject getReject() {
        return this.mReject;
    }

    public UserRemove getUserRemove() {
        return this.mUserRemove;
    }
}
