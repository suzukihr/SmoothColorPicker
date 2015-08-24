package com.github.suzukihr.smoothcolorpicker;

public interface HueValueListener {
    void onHueChanged(float hue, boolean fromUser);
    void onStartTrackingTouch();
    void onStopTrackingTouch();
}