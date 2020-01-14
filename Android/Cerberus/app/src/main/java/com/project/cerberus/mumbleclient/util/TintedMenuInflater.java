package com.project.cerberus.mumbleclient.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.project.cerberus.R;

public class TintedMenuInflater {
    private MenuInflater mInflater;
    private int mTintColour;

    public TintedMenuInflater(Context context) {
        this(context, new MenuInflater(context));
    }

    public TintedMenuInflater(Context context, MenuInflater inflater) {
        this.mInflater = inflater;
        TypedArray actionBarThemeArray = context.obtainStyledAttributes(new int[]{R.attr.actionBarStyle});
        int actionBarTheme = actionBarThemeArray.getResourceId(0, 0);
        actionBarThemeArray.recycle();
        TypedArray titleTextStyleArray = context.obtainStyledAttributes(actionBarTheme, new int[]{R.attr.titleTextStyle});
        int titleTextStyle = titleTextStyleArray.getResourceId(0, 0);
        titleTextStyleArray.recycle();
        TypedArray textColorArray = context.obtainStyledAttributes(titleTextStyle, new int[]{16842904});
        this.mTintColour = textColorArray.getColor(0, 0);
        textColorArray.recycle();
    }

    public void inflate(int menuRes, Menu menu) {
        this.mInflater.inflate(menuRes, menu);
        for (int x = 0; x < menu.size(); x++) {
            tintItem(menu.getItem(x));
        }
    }

    public void tintItem(MenuItem item) {
        if (item.getIcon() != null) {
            item.getIcon().mutate().setColorFilter(this.mTintColour, Mode.MULTIPLY);
        }
    }
}
