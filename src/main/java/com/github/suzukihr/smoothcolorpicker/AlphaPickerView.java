package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AlphaPickerView extends FrameLayout {

    private AlphaView mAlphaView;
    private AlphaOverlayView mAlphaOverlayView;


    public AlphaPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.alpha_picker_view, this);
        mAlphaView = (AlphaView) findViewById(R.id.alphaView);
        mAlphaOverlayView = (AlphaOverlayView) findViewById(R.id.alphaOverlayView);
    }

    public void setColor(int color) {
        mAlphaView.setColor(
                Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)));
    }

    public void setAlpha(float alpha) {
        mAlphaOverlayView.setAlpha(alpha);
    }

    public float getAlpha() {
        return mAlphaOverlayView.getAlpha();
    }

    public int getColor() {
        int alpha = (int) (getAlpha() * 255);
        int color = mAlphaView.getColor();
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public void setListener(AlphaValueListener listener) {
        mAlphaOverlayView.setListener(listener);
    }
}
