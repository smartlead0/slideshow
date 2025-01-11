package com.tech.slideshow.customviews.rangeseekbar;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.tech.slideshow.R;

public class Utils {

    private static final String TAG = "RangeSeekBar";

    public static void print(String log) {
        Log.d(TAG, log);
    }
    public static boolean isFileNameValid(String str) {
        String trim = str.trim();
        return !TextUtils.isEmpty(trim) && trim.matches("[a-zA-Z0-9-_ ]*");
    }
    public static ObjectAnimator startAnim(View view) {
        int dimensionPixelOffset = view.getResources().getDimensionPixelOffset(R.dimen.dp_5);
        float f = (float) (-dimensionPixelOffset);
        float f2 = (float) dimensionPixelOffset;
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X, Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(0.1f, f), Keyframe.ofFloat(0.26f, f2), Keyframe.ofFloat(0.42f, f), Keyframe.ofFloat(0.58f, f2), Keyframe.ofFloat(0.74f, f), Keyframe.ofFloat(0.9f, f2), Keyframe.ofFloat(1.0f, 0.0f))).setDuration(500);
    }
    public static void print(Object... logs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object log : logs) {
            stringBuilder.append(log);
        }
        Log.d(TAG, stringBuilder.toString());
    }

    public static Bitmap drawableToBitmap(Context context, int width, int height, int drawableId) {
        if (context == null || width <= 0 || height <= 0 || drawableId == 0) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Utils.drawableToBitmap(width, height, context.getResources().getDrawable(drawableId, null));
        } else {
            return Utils.drawableToBitmap(width, height, context.getResources().getDrawable(drawableId));
        }
    }

    /**
     * make a drawable to a bitmap
     *
     * @param drawable drawable you want convert
     * @return converted bitmap
     */
    public static Bitmap drawableToBitmap(int width, int height, Drawable drawable) {
        Bitmap bitmap = null;
        try {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && bitmap.getHeight() > 0) {
                    Matrix matrix = new Matrix();
                    float scaleWidth = width * 1.0f / bitmap.getWidth();
                    float scaleHeight = height * 1.0f / bitmap.getHeight();
                    matrix.postScale(scaleWidth, scaleHeight);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    return bitmap;
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * draw 9Path
     *
     * @param canvas Canvas
     * @param bmp    9path bitmap
     * @param rect   9path rect
     */
    public static void drawNinePath(Canvas canvas, Bitmap bmp, Rect rect) {
        NinePatch.isNinePatchChunk(bmp.getNinePatchChunk());
        NinePatch patch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
    }

    public static void drawBitmap(Canvas canvas, Paint paint, Bitmap bmp, Rect rect) {
        try {
            if (NinePatch.isNinePatchChunk(bmp.getNinePatchChunk())) {
                drawNinePath(canvas, bmp, rect);
                return;
            }
        } catch (Exception e) {
        }
        canvas.drawBitmap(bmp, rect.left, rect.top, paint);
    }

    public static int dp2px(Context context, float dpValue) {
        if (context == null || compareFloat(0f, dpValue) == 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Compare the size of two floating point numbers
     *
     * @param a
     * @param b
     * @return 1 is a > b
     * -1 is a < b
     * 0 is a == b
     */
    public static int compareFloat(float a, float b) {
        int ta = Math.round(a * 1000000);
        int tb = Math.round(b * 1000000);
        if (ta > tb) {
            return 1;
        } else if (ta < tb) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Compare the size of two floating point numbers with accuracy
     *
     * @param a
     * @param b
     * @return 1 is a > b
     * -1 is a < b
     * 0 is a == b
     */
    public static int compareFloat(float a, float b, int degree) {
        if (Math.abs(a-b) < Math.pow(0.1, degree)) {
            return 0;
        } else {
            if (a < b) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public static Rect measureText(String text, float textSize) {
        Paint paint = new Paint();
        Rect textRect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
        paint.reset();
        return textRect;
    }

    public static boolean verifyBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled() || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return false;
        }
        return true;
    }

    public static int getColor(Context context, @ColorRes int colorId) {
        if (context != null) {
            return ContextCompat.getColor(context.getApplicationContext(), colorId);
        }
        return Color.WHITE;
    }
}