
package com.tech.slideshow.photopick.internal.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class PreviewViewPager extends ViewPager {

    public PreviewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v instanceof ImageViewTouch) {
//            return ((ImageViewTouch) v).canScroll(dx) || super.canScroll(v, checkV, dx, x, y);
//        }
//        return super.canScroll(v, checkV, dx, x, y);
        return false;
    }
}
