package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RectHueOverlayView extends View {

    private float mHue;
    private Paint mPaint;
    private boolean mFromUser;
    private HueValueListener mListener;

    public RectHueOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float y = event.getY();
        float density = getResources().getDisplayMetrics().density;
        float margin = density * 8;

        if (y < margin) {
            mHue = 360;
        } else if (getHeight() - margin <= y) {
            mHue = 0;
        } else {
            mHue = 360 * (1 - (y - margin) / (getHeight() - margin * 2));
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFromUser = true;
                if (mListener != null) {
                    mListener.onStartTrackingTouch();
                    mListener.onHueChanged(getHue(), mFromUser);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onHueChanged(getHue(), mFromUser);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.onHueChanged(getHue(), mFromUser);
                    mListener.onStopTrackingTouch();
                }
                invalidate();
                mFromUser = false;
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float density = getResources().getDisplayMetrics().density;
        float margin = density * 8;
        float y = getHeight() - margin - (mHue / 360f) * (getHeight() - margin * 2);

        mPaint.setColor(Consts.BORDER_COLOR);
        RectF rectF = new RectF(density, y - density * 3, getWidth() - density, y + density * 3);
        canvas.drawRect(rectF, mPaint);

        mPaint.setColor(Color.WHITE);
        RectF rectF2= new RectF(density * 2, y - density * 2, getWidth() - density * 2, y + density * 2);
        canvas.drawRect(rectF2, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
    }

    public float getHue() {
        return mHue == 360 ? 0 : mHue;
    }

    public void setHue(float hue) {
        mHue = hue;
        invalidate();
    }

    public void setListener(HueValueListener listener) {
        mListener = listener;
    }
}
