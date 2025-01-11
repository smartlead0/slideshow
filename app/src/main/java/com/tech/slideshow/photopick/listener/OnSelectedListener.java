
package com.tech.slideshow.photopick.listener;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

public interface OnSelectedListener {

    void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList);
}
