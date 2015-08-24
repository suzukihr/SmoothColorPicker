package com.github.suzukihr.smoothcolorpicker;

public interface ColorPickerListener {
    void onColorChanged(int color, float[] hsv, boolean fromUser);
    void onStartTrackingTouch();
    void onStopTrackingTouch(int color, float[] hsv);
}