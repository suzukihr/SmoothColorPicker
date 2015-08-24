package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CircleHueOverlayView extends View {

    private float mHue;
    private float mStrokeWidth;
    private Paint mPaint;
    private boolean mFromUser;
    private HueValueListener mListener;

    public CircleHueOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        double angle = Math.atan2(centerX - x, centerY - y) * 180 / Math.PI + 90;
        mHue = 0 <= angle ? (float) angle : (float) angle + 360;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFromUser = true;
                if (mListener != null) {
                    mListener.onStartTrackingTouch();
                    mListener.onHueChanged(mHue, mFromUser);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onHueChanged(mHue, mFromUser);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.onHueChanged(mHue, mFromUser);
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
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float hueRadius = centerX - mStrokeWidth / 2f;
        float pointerRadius = density * 8;

        double radian = mHue * Math.PI / 180;
        double x = centerX + hueRadius * Math.cos(radian);
        double y = centerY - hueRadius * Math.sin(radian);

        mPaint.setColor(Consts.BORDER_COLOR);
        canvas.drawCircle((float) x, (float) y, pointerRadius, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle((float) x, (float) y, pointerRadius - density, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
    }

    public float getHue() {
        return mHue;
    }

    public void setHue(float hue) {
        mHue = hue;
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidate();
    }

    public void setListener(HueValueListener listener) {
        mListener = listener;
    }
}
