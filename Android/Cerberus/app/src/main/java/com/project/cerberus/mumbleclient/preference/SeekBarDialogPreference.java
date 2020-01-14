package com.project.cerberus.mumbleclient.preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import org.spongycastle.i18n.TextBundle;

public class SeekBarDialogPreference extends DialogPreference implements OnSeekBarChangeListener {
    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private Context mContext;
    private int mDefault;
    private String mDialogMessage;
    private int mMax;
    private int mMin;
    private int mMultiplier = 0;
    private SeekBar mSeekBar;
    private TextView mSplashText;
    private String mSuffix;
    private int mValue;
    private TextView mValueText;

    public SeekBarDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mDialogMessage = attrs.getAttributeValue(ANDROID_NS, "dialogMessage");
        this.mSuffix = attrs.getAttributeValue(ANDROID_NS, TextBundle.TEXT_ENTRY);
        this.mDefault = attrs.getAttributeIntValue(ANDROID_NS, "defaultValue", 0);
        this.mMax = attrs.getAttributeIntValue(null, "max", 100);
        this.mMin = attrs.getAttributeIntValue(null, "min", 0);
        this.mMultiplier = attrs.getAttributeIntValue(null, "multiplier", 1);
    }

    protected View onCreateDialogView() {
        LinearLayout layout = new LinearLayout(this.mContext);
        layout.setOrientation(1);
        layout.setPadding(6, 6, 6, 6);
        this.mSplashText = new TextView(this.mContext);
        if (this.mDialogMessage != null) {
            this.mSplashText.setText(this.mDialogMessage);
        }
        layout.addView(this.mSplashText);
        this.mValueText = new TextView(this.mContext);
        this.mValueText.setGravity(1);
        this.mValueText.setTextSize(18.0f);
        layout.addView(this.mValueText, new LayoutParams(-1, -2));
        this.mSeekBar = new SeekBar(this.mContext);
        this.mSeekBar.setOnSeekBarChangeListener(this);
        layout.addView(this.mSeekBar, new LayoutParams(-1, -2));
        if (shouldPersist()) {
            this.mValue = getPersistedInt(-1) != -1 ? getPersistedInt(this.mDefault) / this.mMultiplier : getPersistedInt(this.mDefault);
        }
        this.mSeekBar.setMax(this.mMax - this.mMin);
        this.mSeekBar.setProgress(this.mValue - this.mMin);
        return layout;
    }

    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        this.mSeekBar.setMax(this.mMax - this.mMin);
        this.mSeekBar.setProgress(this.mValue - this.mMin);
    }

    protected void onSetInitialValue(boolean restore, Object defaultValue) {
        super.onSetInitialValue(restore, defaultValue);
        if (restore) {
            this.mValue = shouldPersist() ? getPersistedInt(this.mDefault) / this.mMultiplier : 0;
        } else {
            this.mValue = ((Integer) defaultValue).intValue() / this.mMultiplier;
        }
    }

    public void onProgressChanged(SeekBar seek, int value, boolean fromTouch) {
        String t = String.valueOf((this.mMultiplier * value) + (this.mMultiplier * this.mMin));
        TextView textView = this.mValueText;
        if (this.mSuffix != null) {
            t = t.concat(this.mSuffix);
        }
        textView.setText(t);
    }

    public void onStartTrackingTouch(SeekBar seek) {
    }

    public void onStopTrackingTouch(SeekBar seek) {
    }

    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult && shouldPersist()) {
            persistInt(this.mMultiplier * (this.mMin + this.mSeekBar.getProgress()));
        }
    }
}
