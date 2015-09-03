package com.github.suzukihr.smoothcolorpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CircleColorPickerView extends FrameLayout {

    private SvView mSvView;
    private SvOverlayView mSvOverlayView;
    private CircleHueOverlayView mCircleHueOverlayView;
    private ColorPickerListener mListener;

    public CircleColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.color_picker_view, this);

        mSvView = (SvView) findViewById(R.id.svView);
        mCircleHueOverlayView = (CircleHueOverlayView) findViewById(R.id.hueOverlayView);
        mSvOverlayView = (SvOverlayView) findViewById(R.id.svOverlayView);

        mCircleHueOverlayView.setListener(new HueValueListener() {
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float strokeWidth = Math.min(getWidth(), getHeight()) / 8f;
        CircleHueView circleHueView = (CircleHueView) findViewById(R.id.circleHueView);
        circleHueView.setStrokeWidth(strokeWidth);
        mCircleHueOverlayView.setStrokeWidth(strokeWidth);
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
        mCircleHueOverlayView.setHue(hsv[0]);
        mSvView.setHue(hsv[0]);
        mSvOverlayView.setSv(hsv[1], hsv[2]);
    }

    public int getColor() {
        return Color.HSVToColor(getHsv());
    }

    public float[] getHsv() {
        float[] hsv = new float[3];
        hsv[0] = mCircleHueOverlayView.getHue();
        hsv[1] = mSvOverlayView.getSaturation();
        hsv[2] = mSvOverlayView.getValue();
        return hsv;
    }

    public void setListener(ColorPickerListener listener) {
        mListener = listener;
    }
}
