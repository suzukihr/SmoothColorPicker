package com.github.suzukihr.smoothcolorpicker;

public interface AlphaValueListener {
    void onAlphaChanged(float alpha, boolean fromUser);
    void onStartTrackingTouch();
    void onStopTrackingTouch(float alpha);
}