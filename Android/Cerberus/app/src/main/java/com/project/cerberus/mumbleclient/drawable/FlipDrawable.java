package com.project.cerberus.mumbleclient.drawable;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

@TargetApi(11)
public class FlipDrawable extends LayerDrawable implements AnimatorUpdateListener {
    private Camera mCamera = new Camera();
    private Drawable mFrom;
    private float mRotate = 0.0f;
    private Drawable mTo;

    public FlipDrawable(Drawable from, Drawable to) {
        super(new Drawable[]{from, to});
        this.mFrom = from;
        this.mTo = to;
    }

    public void draw(Canvas canvas) {
        float centerX = (float) (getIntrinsicWidth() / 2);
        float centerY = (float) (getIntrinsicHeight() / 2);
        boolean flipped = this.mRotate > 90.0f;
        this.mCamera.save();
        this.mCamera.translate(centerX, centerY, 0.0f);
        if (flipped) {
            this.mCamera.rotateY(180.0f - this.mRotate);
        } else {
            this.mCamera.rotateY(this.mRotate);
        }
        this.mCamera.translate(-centerX, -centerY, 0.0f);
        this.mCamera.applyToCanvas(canvas);
        this.mCamera.restore();
        if (flipped) {
            this.mTo.draw(canvas);
        } else {
            this.mFrom.draw(canvas);
        }
    }

    public Drawable getCurrent() {
        return this.mRotate > 90.0f ? this.mTo : this.mFrom;
    }

    public void start(long duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 180.0f});
        animator.setDuration(duration);
        animator.addUpdateListener(this);
        animator.start();
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        this.mRotate = ((Float) animation.getAnimatedValue()).floatValue();
        invalidateSelf();
    }
}
