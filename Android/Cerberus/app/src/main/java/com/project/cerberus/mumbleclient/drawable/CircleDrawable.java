package com.project.cerberus.mumbleclient.drawable;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.TypedValue;
import com.project.cerberus.R;

public class CircleDrawable extends Drawable {
    public static final int STROKE_WIDTH_DP = 1;
    private Bitmap mBitmap;
    private ConstantState mConstantState;
    private Paint mPaint = new Paint();
    private Resources mResources;
    private Paint mStrokePaint;

    /* renamed from: com.project.cerberus.mumbleclient.drawable.CircleDrawable$1 */
    class C02531 extends ConstantState {
        C02531() {
        }

        public Drawable newDrawable() {
            return new CircleDrawable(CircleDrawable.this.mResources, CircleDrawable.this.mBitmap);
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }

    public CircleDrawable(Resources resources, Bitmap bitmap) {
        this.mResources = resources;
        this.mBitmap = bitmap;
        this.mPaint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
        this.mPaint.setDither(true);
        this.mPaint.setAntiAlias(true);
        this.mStrokePaint = new Paint();
        this.mStrokePaint.setDither(true);
        this.mStrokePaint.setAntiAlias(true);
        this.mStrokePaint.setColor(resources.getColor(R.color.ripple_talk_state_disabled));
        this.mStrokePaint.setStrokeWidth(TypedValue.applyDimension(1, 1.0f, resources.getDisplayMetrics()));
        this.mStrokePaint.setStyle(Style.STROKE);
        this.mConstantState = new C02531();
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        RectF bitmapRect = new RectF(0.0f, 0.0f, (float) this.mBitmap.getWidth(), (float) this.mBitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setRectToRect(bitmapRect, new RectF(bounds), ScaleToFit.CENTER);
        this.mPaint.getShader().setLocalMatrix(matrix);
    }

    public void draw(Canvas canvas) {
        RectF imageRect = new RectF(getBounds());
        RectF strokeRect = new RectF(getBounds());
        strokeRect.inset(this.mStrokePaint.getStrokeWidth() / 2.0f, this.mStrokePaint.getStrokeWidth() / 2.0f);
        canvas.drawOval(imageRect, this.mPaint);
        canvas.drawOval(strokeRect, this.mStrokePaint);
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    @SuppressLint("WrongConstant")
    public int getOpacity() {
        return 0;
    }

    public ConstantState getConstantState() {
        return this.mConstantState;
    }
}
