package com.project.cerberus.jumble.net;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class JumbleNetworkThread implements Runnable {
    private ExecutorService mExecutor;
    private boolean mInitialized;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private ExecutorService mReceiveExecutor;
    private ExecutorService mSendExecutor;

    protected void startThreads() {
        if (this.mInitialized) {
            throw new IllegalArgumentException("Threads already initialized.");
        }
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mSendExecutor = Executors.newSingleThreadExecutor();
        this.mReceiveExecutor = Executors.newSingleThreadExecutor();
        this.mExecutor.execute(this);
        this.mInitialized = true;
    }

    protected void stopThreads() {
        if (this.mInitialized) {
            this.mSendExecutor.shutdown();
            this.mReceiveExecutor.shutdown();
            this.mExecutor.shutdown();
            this.mSendExecutor = null;
            this.mReceiveExecutor = null;
            this.mExecutor = null;
            this.mInitialized = false;
            return;
        }
        throw new IllegalArgumentException("Threads already shutdown.");
    }

    protected void executeOnSendThread(Runnable r) {
        this.mSendExecutor.execute(r);
    }

    protected void executeOnReceiveThread(Runnable r) {
        this.mSendExecutor.execute(r);
    }

    protected void executeOnMainThread(Runnable r) {
        this.mMainHandler.post(r);
    }

    protected Handler getMainHandler() {
        return this.mMainHandler;
    }
}
