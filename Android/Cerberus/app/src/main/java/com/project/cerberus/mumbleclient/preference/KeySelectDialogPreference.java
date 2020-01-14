package com.project.cerberus.mumbleclient.preference;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build.VERSION;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.project.cerberus.R;

public class KeySelectDialogPreference extends DialogPreference implements OnKeyListener {
    private int keyCode;
    private TextView valueTextView;

    /* renamed from: com.project.cerberus.mumbleclient.preference.KeySelectDialogPreference$1 */
    class C02541 implements OnClickListener {
        C02541() {
        }

        public void onClick(DialogInterface dialog, int which) {
            KeySelectDialogPreference.this.keyCode = 0;
            KeySelectDialogPreference.this.valueTextView.setText("No Key");
            KeySelectDialogPreference.this.persistInt(KeySelectDialogPreference.this.keyCode);
        }
    }

    public KeySelectDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onPrepareDialogBuilder(Builder builder) {
        super.onPrepareDialogBuilder(builder);
        builder.setOnKeyListener(this);
        builder.setNeutralButton(R.string.reset_key, new C02541());
    }

    protected View onCreateDialogView() {
        LayoutParams params = new LayoutParams(-1, -2);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(1);
        layout.setPadding(6, 6, 6, 6);
        TextView promptTextView = new TextView(getContext());
        promptTextView.setText(R.string.pressKey);
        promptTextView.setGravity(1);
        this.valueTextView = new TextView(getContext());
        this.valueTextView.setTextSize(2, 22.0f);
        this.valueTextView.setGravity(1);
        this.valueTextView.setPadding(0, 12, 0, 12);
        TextView alertTextView = new TextView(getContext());
        alertTextView.setText(R.string.pressKeyInfo);
        alertTextView.setGravity(1);
        alertTextView.setTextSize(2, 12.0f);
        layout.addView(promptTextView, params);
        layout.addView(this.valueTextView, params);
        layout.addView(alertTextView);
        return layout;
    }

    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        if (restorePersistedValue) {
            this.keyCode = getPersistedInt(0);
        } else {
            this.keyCode = ((Integer) defaultValue).intValue();
        }
    }

    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            persistInt(this.keyCode);
        }
        super.onDialogClosed(positiveResult);
    }

    @TargetApi(12)
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        if (this.keyCode == 0) {
            this.valueTextView.setText("No Key");
        } else if (VERSION.SDK_INT >= 12) {
            this.valueTextView.setText(KeyEvent.keyCodeToString(this.keyCode));
        } else {
            this.valueTextView.setText("Key code: " + this.keyCode);
        }
    }

    @TargetApi(12)
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            this.keyCode = keyCode;
            if (VERSION.SDK_INT >= 12) {
                this.valueTextView.setText(KeyEvent.keyCodeToString(keyCode));
            } else {
                this.valueTextView.setText("Key code: " + keyCode);
            }
        } else {
            dialog.dismiss();
        }
        return true;
    }
}
