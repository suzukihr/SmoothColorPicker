package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class RectHueView extends View {

    public RectHueView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int[] mColors = {0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000};
        LinearGradient lg = new LinearGradient(0, 0, 0, getHeight(), mColors, null, Shader.TileMode.CLAMP);

        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(lg);
        canvas.drawRect(rectF, paint);
    }
}
