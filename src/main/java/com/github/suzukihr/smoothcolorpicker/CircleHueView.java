package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class CircleHueView extends View {

    private float mStrokeWidth;

    public CircleHueView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawBorder(canvas);

        int[] mColors = {0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000};
        SweepGradient sg = new SweepGradient(getWidth() / 2, getHeight() / 2, mColors, null);

        float density = getResources().getDisplayMetrics().density;
        float strokeWidth = mStrokeWidth - density * 2;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setShader(sg);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - mStrokeWidth / 2, paint);
    }

    private void drawBorder(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Consts.BORDER_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - mStrokeWidth / 2, paint);
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidate();
    }
}
