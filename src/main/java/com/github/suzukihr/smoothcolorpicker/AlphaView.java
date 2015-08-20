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

public class AlphaView extends View {

    private int mColor = 0xffff0000;

    public AlphaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.bg_alpha_view);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        Shader shader = new LinearGradient(0, 0, getWidth(), 0, Color.TRANSPARENT, mColor, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), paint);
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();
    }

    public int getColor() {
        return mColor;
    }
}
