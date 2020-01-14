package com.project.cerberus.mumbleclient.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.project.cerberus.R;

public class PlumbleHotCorner implements OnTouchListener {
    private Context mContext;
    private int mHighlightColour;
    private PlumbleHotCornerListener mListener;
    private LayoutParams mParams;
    private boolean mShown;
    private View mView;
    private WindowManager mWindowManager;

    public interface PlumbleHotCornerListener {
        void onHotCornerDown();

        void onHotCornerUp();
    }

    public PlumbleHotCorner(Context context, int gravity, PlumbleHotCornerListener listener) {
        if (listener == null) {
            throw new NullPointerException("A PlumbleHotCornerListener must be assigned.");
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mContext = context;
        this.mView = inflater.inflate(R.layout.ptt_corner, null, false);
        this.mView.setOnTouchListener(this);
        this.mListener = listener;
        this.mParams = new LayoutParams(-2, -2, 2003, 262152, -3);
        this.mParams.gravity = gravity;
        this.mHighlightColour = this.mContext.getResources().getColor(R.color.holo_blue_bright);
    }

    private void updateLayout() {
        if (isShown()) {
            this.mWindowManager.updateViewLayout(this.mView, this.mParams);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mView.setBackgroundColor(this.mHighlightColour);
                this.mListener.onHotCornerDown();
                return true;
            case 1:
                this.mView.setBackgroundColor(0);
                this.mListener.onHotCornerUp();
                return true;
            default:
                return false;
        }
    }

    public void setShown(boolean shown) {
        if (shown != this.mShown) {
            if (shown) {
                this.mWindowManager.addView(this.mView, this.mParams);
            } else {
                this.mWindowManager.removeView(this.mView);
            }
            this.mShown = shown;
        }
    }

    public boolean isShown() {
        return this.mShown;
    }

    public void setGravity(int gravity) {
        this.mParams.gravity = gravity;
        updateLayout();
    }

    public int getGravity() {
        return this.mParams.gravity;
    }
}
