package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RectColorPickerView extends LinearLayout {

    private SvView mSvView;
    private SvOverlayView mSvOverlayView;
    private RectHueOverlayView mRectHueOverlayView;
    private ColorPickerListener mListener;

    public RectColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.rect_color_picker_view, this);
        setOrientation(HORIZONTAL);

        mSvView = (SvView) findViewById(R.id.svView);
        mRectHueOverlayView = (RectHueOverlayView) findViewById(R.id.rectHueOverlayView);
        mSvOverlayView = (SvOverlayView) findViewById(R.id.svOverlayView);

        mRectHueOverlayView.setListener(new HueValueListener() {
            @Override
            public void onHueChanged(float hue, boolean fromUser) {
                mSvView.setHue(hue);
                if (mListener != null) {
                    mListener.onColorChanged(getColor(), getHsv(), fromUser);
                }
            }

            @Override
            public void onStartTrackingTouch() {
                if (mListener != null) {
                    mListener.onStartTrackingTouch();
                }
            }

            @Override
            public void onStopTrackingTouch() {
                if (mListener != null) {
                    mListener.onStopTrackingTouch(getColor(), getHsv());
                }
            }
        });

        mSvOverlayView.setListener(new SvOverlayView.SvValueListener() {
            @Override
            public void onSvChanged(float s, float v, boolean fromUser) {
                if (mListener != null) {
                    mListener.onColorChanged(getColor(), getHsv(), fromUser);
                }
            }

            @Override
            public void onStartTrackingTouch() {
                if (mListener != null) {
                    mListener.onStartTrackingTouch();
                }
            }

            @Override
            public void onStopTrackingTouch() {
                if (mListener != null) {
                    mListener.onStopTrackingTouch(getColor(), getHsv());
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
    }

    public void setColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        setHsv(hsv);
    }

    public void setHsv(float[] hsv) {
        mRectHueOverlayView.setHue(hsv[0]);
        mSvView.setHue(hsv[0]);
        mSvOverlayView.setSv(hsv[1], hsv[2]);
    }

    public int getColor() {
        return Color.HSVToColor(getHsv());
    }

    public float[] getHsv() {
        float[] hsv = new float[3];
        hsv[0] = mRectHueOverlayView.getHue();
        hsv[1] = mSvOverlayView.getSaturation();
        hsv[2] = mSvOverlayView.getValue();
        return hsv;
    }

    public void setListener(ColorPickerListener listener) {
        mListener = listener;
    }
}
