package com.project.cerberus.mumbleclient.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.text.Html.ImageGetter;
import android.util.Base64;
import android.util.DisplayMetrics;
import ch.boye.httpclientandroidlib.protocol.HTTP;
import com.project.cerberus.mumbleclient.Settings;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MumbleImageGetter implements ImageGetter {
    private static final int MAX_LENGTH = 64000;
    private Map<String, Drawable> mBitmapCache = new HashMap();
    private Context mContext;
    private Settings mSettings;

    public MumbleImageGetter(Context context) {
        this.mContext = context;
        this.mSettings = Settings.getInstance(context);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
    }

    public Drawable getDrawable(String source) {
        Drawable cachedDrawable = (Drawable) this.mBitmapCache.get(source);
        if (cachedDrawable != null) {
            return cachedDrawable;
        }
        try {
            String decodedSource = URLDecoder.decode(source, HTTP.UTF_8);
            Bitmap bitmap = null;
            try {
                if (decodedSource.startsWith("data:image")) {
                    bitmap = getBase64Image(decodedSource.split(",")[1]);
                } else if (this.mSettings.shouldLoadExternalImages()) {
                    bitmap = getURLImage(decodedSource);
                }
                if (bitmap == null) {
                    return null;
                }
                Drawable drawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
                DisplayMetrics metrics = this.mContext.getResources().getDisplayMetrics();
                drawable.setBounds(0, 0, (int) (((float) drawable.getIntrinsicWidth()) * metrics.density), (int) (((float) drawable.getIntrinsicHeight()) * metrics.density));
                this.mBitmapCache.put(source, drawable);
                return drawable;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private Bitmap getBase64Image(String base64) throws IllegalArgumentException {
        byte[] src = Base64.decode(base64, 0);
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

    private Bitmap getURLImage(String source) {
        Bitmap bitmap = null;
        try {
            URLConnection conn = new URL(source).openConnection();
            if (conn.getContentLength() <= MAX_LENGTH) {
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bitmap;
    }
}
