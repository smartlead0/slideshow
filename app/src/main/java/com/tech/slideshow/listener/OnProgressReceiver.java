package com.tech.slideshow.listener;

public interface OnProgressReceiver {
    void onImageProgressFrameUpdate(float f);

    void onImageProgressFinish();

    void onProgressFinish(String str);

    void onVideoProgressFrameUpdate(float f);
}
