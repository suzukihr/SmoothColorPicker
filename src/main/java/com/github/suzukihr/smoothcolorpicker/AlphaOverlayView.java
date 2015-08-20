package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AlphaOverlayView extends View {

    private float mAlpha;
    private Paint mPaint;
    private boolean mFromUser;
    private AlphaValueListener mListener;

    public AlphaOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float density = getResources().getDisplayMetrics().density;
        float margin = density * 4;

        if (x < margin) {
            mAlpha = 0;
        } else if (getWidth() - margin <= x) {
            mAlpha = 1;
        } else {
            mAlpha = (x - margin) / (getWidth() - margin * 2);
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
        float margin = density * 4;
        float x = margin + mAlpha * (getWidth() - margin * 2);

        mPaint.setColor(Consts.BORDER_COLOR);
        RectF rectF = new RectF(x - density * 3, density * 2, x + density * 3, getHeight() - density * 2);
        canvas.drawRect(rectF, mPaint);

        mPaint.setColor(Color.WHITE);
        RectF rectF2 = new RectF(x - density * 2, density * 3, x + density * 2, getHeight() - density * 3);
        canvas.drawRect(rectF2, mPaint);

        if (mListener != null) {
            mListener.onAlphaChanged(mAlpha, mFromUser);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
        invalidate();
    }

    public void setListener(AlphaValueListener listener) {
        mListener = listener;
    }
}
