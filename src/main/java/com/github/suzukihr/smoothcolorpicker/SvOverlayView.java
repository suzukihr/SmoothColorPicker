package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SvOverlayView extends View {

    private float mSaturation;
    private float mValue;
    private Paint mPaint;
    private boolean mFromUser;
    private SvValueListener mListener;

    public SvOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        float density = getResources().getDisplayMetrics().density;
        float radius = density * 8;

        if (x < radius) {
            mSaturation = 0;
        } else if (getWidth() - radius < x) {
            mSaturation = 1;
        } else {
            mSaturation = (x - radius) / (getWidth() - density * 16);
        }

        if (y < radius) {
            mValue = 1;
        } else if (getHeight() - radius < y) {
            mValue = 0;
        } else {
            mValue = 1 - (y - radius) / (getHeight() - density * 16);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFromUser = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                mFromUser = false;
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float density = getResources().getDisplayMetrics().density;
        float radius = density * 8;

        float x = radius + mSaturation * (getWidth() - density * 16);
        float y = radius + (1 - mValue) * (getHeight() - density * 16);

        mPaint.setColor(Consts.BORDER_COLOR);
        canvas.drawCircle(x, y, radius, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, radius - density, mPaint);

        if (mListener != null) {
            mListener.onSvChanged(mSaturation, mValue, mFromUser);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
    }

    public float getSaturation() {
        return mSaturation;
    }

    public float getValue() {
        return mValue;
    }

    public void setSv(float s, float v) {
        mSaturation = s;
        mValue = v;
        invalidate();
    }

    public void setListener(SvValueListener listener) {
        mListener = listener;
    }

    public interface SvValueListener {
        void onSvChanged(float s, float v, boolean fromUser);
    }
}
