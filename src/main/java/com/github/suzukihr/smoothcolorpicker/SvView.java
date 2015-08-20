package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class SvView extends View {

    private float mHue = 0;
    private Paint mPaint;
    private RectF mRectF;

    public SvView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRectF = new RectF(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int color = Color.HSVToColor(new float[]{mHue, 1, 1});
        Shader shaderHorizontal = new LinearGradient(0, 0, getWidth(), 0, Color.WHITE, color, Shader.TileMode.CLAMP);
        mPaint.setShader(shaderHorizontal);
        canvas.drawRect(mRectF, mPaint);

        Shader shaderVertical = new LinearGradient(0, 0, 0, getHeight(), Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);
        mPaint.setShader(shaderVertical);
        canvas.drawRect(mRectF, mPaint);
    }

    public void setHue(float hue) {
        mHue = hue;
        invalidate();
    }
}
