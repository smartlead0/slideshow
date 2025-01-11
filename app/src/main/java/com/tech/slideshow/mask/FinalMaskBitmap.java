package com.tech.slideshow.mask;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.core.internal.view.SupportMenu;


import com.tech.slideshow.MyApplication;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class FinalMaskBitmap {
    public static float ANIMATED_FRAME = 22.0f;
    private static final int ANIMATED_FRAME_CAL = ((int) (ANIMATED_FRAME - 1.0f));
    private static int averageHeight;
    private static int averageWidth;
    private static float axisX;
    private static float axisY;
    private static Bitmap[][] bitmaps;
    public static Camera camera = new Camera();

    public static int direction = 0;


    public static Matrix matrix = new Matrix();


    private static final Paint paint = new Paint();
    public static int partNumber = 8;

    private static int[][] randRect = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{20, 20}));
    private static final Random random = new Random();
    public static EFFECT rollMode;
    private static float rotateDegree;

    public enum EFFECT {
        CIRCLE_LEFT_TOP("CIRCLE LEFT TOP") {
            public Bitmap getMask(int width, int height, int factor) {


                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, 0.0f, (((float) Math.sqrt((width * width) + (height * height))) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_RIGHT_TOP("Circle right top") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle((float) width, 0.0f, (((float) Math.sqrt((width * width) + (height * height))) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_LEFT_BOTTOM("Circle left bottom") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, (float) height, (((float) Math.sqrt((width * width) + (height * height))) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_RIGHT_BOTTOM("Circle right bottom") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle((float) width, (float) height, (((float) Math.sqrt((width * width) + (height * height))) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_IN("Circle in") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.BLACK);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float r = FinalMaskBitmap.getRad(width * 2, height * 2);
                float f = (r / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                paint.setColor(Color.BLACK);
                canvas.drawColor(Color.BLACK);
                paint.setColor(Color.TRANSPARENT);
                paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
                canvas.drawCircle(((float) width) / 2.0f, ((float) height) / 2.0f, r - f, paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_OUT("Circle out") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(((float) width) / 2.0f, ((float) height) / 2.0f, (FinalMaskBitmap.getRad(width * 2, height * 2) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CROSS_IN("Cross in") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float fx = (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float fy = (((float) height) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.lineTo(fx, 0.0f);
                path.lineTo(fx, fy);
                path.lineTo(0.0f, fy);
                path.lineTo(0.0f, 0.0f);
                path.close();
                path.moveTo((float) width, 0.0f);
                path.lineTo(((float) width) - fx, 0.0f);
                path.lineTo(((float) width) - fx, fy);
                path.lineTo((float) width, fy);
                path.lineTo((float) width, 0.0f);
                path.close();
                path.moveTo((float) width, (float) height);
                path.lineTo(((float) width) - fx, (float) height);
                path.lineTo(((float) width) - fx, ((float) height) - fy);
                path.lineTo((float) width, ((float) height) - fy);
                path.lineTo((float) width, (float) height);
                path.close();
                path.moveTo(0.0f, (float) height);
                path.lineTo(fx, (float) height);
                path.lineTo(fx, ((float) height) - fy);
                path.lineTo(0.0f, ((float) height) - fy);
                path.lineTo(0.0f, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        CROSS_OUT("Cross out") {
            public Bitmap getMask(int width, int height, int factor) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float fx = (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float fy = (((float) height) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo((((float) width) / 2.0f) + fx, 0.0f);
                path.lineTo((((float) width) / 2.0f) + fx, (((float) height) / 2.0f) - fy);
                path.lineTo((float) width, (((float) height) / 2.0f) - fy);
                path.lineTo((float) width, (((float) height) / 2.0f) + fy);
                path.lineTo((((float) width) / 2.0f) + fx, (((float) height) / 2.0f) + fy);
                path.lineTo((((float) width) / 2.0f) + fx, (float) height);
                path.lineTo((((float) width) / 2.0f) - fx, (float) height);
                path.lineTo((((float) width) / 2.0f) - fx, (((float) height) / 2.0f) - fy);
                path.lineTo(0.0f, (((float) height) / 2.0f) - fy);
                path.lineTo(0.0f, (((float) height) / 2.0f) + fy);
                path.lineTo((((float) width) / 2.0f) - fx, (((float) height) / 2.0f) + fy);
                path.lineTo((((float) width) / 2.0f) - fx, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        DIAMOND_IN("Diamond in") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                float fx = (((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                float fy = (((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                path.moveTo(((float) width) / 2.0f, (((float) height) / 2.0f) - fy);
                path.lineTo((((float) width) / 2.0f) + fx, ((float) height) / 2.0f);
                path.lineTo(((float) width) / 2.0f, (((float) height) / 2.0f) + fy);
                path.lineTo((((float) width) / 2.0f) - fx, ((float) height) / 2.0f);
                path.lineTo(((float) width) / 2.0f, (((float) height) / 2.0f) - fy);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        DIAMOND_OUT("Diamond out") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.BLACK);
                paint.setColor(Color.TRANSPARENT);
                paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawColor(Color.BLACK);
                Path path = new Path();
                float fx = ((float) width) - ((((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor));
                float fy = ((float) height) - ((((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor));
                path.moveTo(((float) width) / 2.0f, (((float) height) / 2.0f) - fy);
                path.lineTo((((float) width) / 2.0f) + fx, ((float) height) / 2.0f);
                path.lineTo(((float) width) / 2.0f, (((float) height) / 2.0f) + fy);
                path.lineTo((((float) width) / 2.0f) - fx, ((float) height) / 2.0f);
                path.lineTo(((float) width) / 2.0f, (((float) height) / 2.0f) - fy);
                path.close();
                paint.setColor(Color.TRANSPARENT);
                paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        ECLIPSE_IN("Eclipse in") {
            public Bitmap getMask(int width, int height, int factor) {

                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float hf = (((float) height) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float wf = (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                RectF rectFL = new RectF(-wf, 0.0f, wf, (float) height);
                RectF rectFT = new RectF(0.0f, -hf, (float) width, hf);
                RectF rectFR = new RectF(((float) width) - wf, 0.0f, ((float) width) + wf, (float) height);
                RectF rectFB = new RectF(0.0f, ((float) height) - hf, (float) width, ((float) height) + hf);
                canvas.drawOval(rectFL, FinalMaskBitmap.paint);
                canvas.drawOval(rectFT, FinalMaskBitmap.paint);
                canvas.drawOval(rectFR, FinalMaskBitmap.paint);
                canvas.drawOval(rectFB, FinalMaskBitmap.paint);
                drawText(canvas);
                return mask;
            }
        },
        FOUR_TRIANGLE("Four triangle") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) height) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(ratioX, 0.0f);
                path.lineTo((float) width, ((float) height) - ratioY);
                path.lineTo((float) width, (float) height);
                path.lineTo(((float) width) - ratioX, (float) height);
                path.lineTo(0.0f, ratioY);
                path.close();
                path.moveTo(((float) width) - ratioX, 0.0f);
                path.lineTo((float) width, 0.0f);
                path.lineTo((float) width, ratioY);
                path.lineTo(ratioX, (float) height);
                path.lineTo(0.0f, (float) height);
                path.lineTo(0.0f, ((float) height) - ratioY);
                path.lineTo(((float) width) - ratioX, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        HORIZONTAL_RECT("Horizontal rect") {
            public Bitmap getMask(int width, int height, int factor) {

                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float wf = ((float) width) / 10.0f;
                float rectW = (wf / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                for (int i = 0; i < 10; i++) {
                    canvas.drawRect(new Rect((int) (((float) i) * wf), 0, (int) ((((float) i) * wf) + rectW), height), paint);
                }
                drawText(canvas);
                return mask;
            }
        },
        HORIZONTAL_COLUMN_DOWNMASK("Horizontal column downmask") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float factorX = ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) / 2.0f;
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) / 2.0f)) * ((float) factor), ((float) height) / 2.0f), 0.0f, 0.0f, paint);
                if (((float) factor) >= 0.5f + factorX) {
                    canvas.drawRoundRect(new RectF(((float) width) - ((((float) width) / (((float) (FinalMaskBitmap.ANIMATED_FRAME_CAL - 1)) / 2.0f)) * ((float) ((int) (((float) factor) - factorX)))), ((float) height) / 2.0f, (float) width, (float) height), 0.0f, 0.0f, paint);
                }
                return mask;
            }
        },
        LEAF("LEAF") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeCap(Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                float fx = (float) ((width / FinalMaskBitmap.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((height / FinalMaskBitmap.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                path.moveTo(0.0f, (float) height);
                path.cubicTo(0.0f, (float) height, (((float) width) / 2.0f) - fx, (((float) height) / 2.0f) - fy, (float) width, 0.0f);
                path.cubicTo((float) width, 0.0f, (((float) width) / 2.0f) + fx, (((float) height) / 2.0f) + fy, 0.0f, (float) height);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        OPEN_DOOR("OPEN_DOOR") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeCap(Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                float fx = (float) ((width / FinalMaskBitmap.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((height / FinalMaskBitmap.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                path.moveTo((float) (width / 2), 0.0f);
                path.lineTo(((float) (width / 2)) - fx, 0.0f);
                path.lineTo(((float) (width / 2)) - (fx / 2.0f), (float) (height / 6));
                path.lineTo(((float) (width / 2)) - (fx / 2.0f), (float) (height - (height / 6)));
                path.lineTo(((float) (width / 2)) - fx, (float) height);
                path.lineTo(((float) (width / 2)) + fx, (float) height);
                path.lineTo(((float) (width / 2)) + (fx / 2.0f), (float) (height - (height / 6)));
                path.lineTo(((float) (width / 2)) + (fx / 2.0f), (float) (height / 6));
                path.lineTo(((float) (width / 2)) + fx, 0.0f);
                path.lineTo(((float) (width / 2)) - fx, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        PIN_WHEEL("PIN_WHEEL") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float rationX = (((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                float rationY = (((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(((float) width) / 2.0f, ((float) height) / 2.0f);
                path.lineTo(0.0f, (float) height);
                path.lineTo(rationX, (float) height);
                path.close();
                path.moveTo(((float) width) / 2.0f, ((float) height) / 2.0f);
                path.lineTo((float) width, (float) height);
                path.lineTo((float) width, ((float) height) - rationY);
                path.close();
                path.moveTo(((float) width) / 2.0f, ((float) height) / 2.0f);
                path.lineTo((float) width, 0.0f);
                path.lineTo(((float) width) - rationX, 0.0f);
                path.close();
                path.moveTo(((float) width) / 2.0f, ((float) height) / 2.0f);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(0.0f, rationY);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        RECT_RANDOM("RECT_RANDOM") {
            public Bitmap getMask(int width, int height, int factor) {

                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float wf = (float) (width / FinalMaskBitmap.ANIMATED_FRAME_CAL);
                float hf = (float) (height / FinalMaskBitmap.ANIMATED_FRAME_CAL);
                for (int i = 0; i < FinalMaskBitmap.randRect.length; i++) {
                    int rand = FinalMaskBitmap.random.nextInt(FinalMaskBitmap.randRect[i].length);
                    while (FinalMaskBitmap.randRect[i][rand] == 1) {
                        rand = FinalMaskBitmap.random.nextInt(FinalMaskBitmap.randRect[i].length);
                    }
                    FinalMaskBitmap.randRect[i][rand] = 1;
                    for (int j = 0; j < FinalMaskBitmap.randRect[i].length; j++) {
                        if (FinalMaskBitmap.randRect[i][j] == 1) {
                            canvas.drawRoundRect(new RectF(((float) i) * wf, ((float) j) * hf, (((float) i) + 1.0f) * wf, (((float) j) + 1.0f) * hf), 0.0f, 0.0f, paint);
                        }
                    }
                }
                drawText(canvas);
                return mask;
            }
        },
        SKEW_LEFT_MEARGE("SKEW_LEFT_MEARGE") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(ratioX, 0.0f);
                path.lineTo(0.0f, 0.0f);
                path.close();
                path.moveTo(((float) width) - ratioX, (float) height);
                path.lineTo((float) width, ((float) height) - ratioY);
                path.lineTo((float) width, (float) height);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SKEW_LEFT_SPLIT("SKEW_LEFT_SPLIT") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(ratioX, 0.0f);
                path.lineTo((float) width, ((float) height) - ratioY);
                path.lineTo((float) width, (float) height);
                path.lineTo(((float) width) - ratioX, (float) height);
                path.lineTo(0.0f, ratioY);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SKEW_RIGHT_SPLIT("SKEW_RIGHT_SPLIT") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(((float) width) - ratioX, 0.0f);
                path.lineTo((float) width, 0.0f);
                path.lineTo((float) width, ratioY);
                path.lineTo(ratioX, (float) height);
                path.lineTo(0.0f, (float) height);
                path.lineTo(0.0f, ((float) height) - ratioY);
                path.lineTo(((float) width) - ratioX, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SKEW_RIGHT_MEARGE("SKEW_RIGHT_MEARGE") {
            public Bitmap getMask(int width, int height, int factor) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) width) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) height) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ((float) height) - ratioY);
                path.lineTo(ratioX, (float) height);
                path.lineTo(0.0f, (float) height);
                path.close();
                path.moveTo(((float) width) - ratioX, 0.0f);
                path.lineTo((float) width, ratioY);
                path.lineTo((float) width, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SQUARE_IN("SQUARE_IN") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) height) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.lineTo(0.0f, (float) height);
                path.lineTo(ratioX, (float) height);
                path.lineTo(ratioX, 0.0f);
                path.moveTo((float) width, (float) height);
                path.lineTo((float) width, 0.0f);
                path.lineTo(((float) width) - ratioX, 0.0f);
                path.lineTo(((float) width) - ratioX, (float) height);
                path.moveTo(ratioX, ratioY);
                path.lineTo(ratioX, 0.0f);
                path.lineTo(((float) width) - ratioX, 0.0f);
                path.lineTo(((float) width) - ratioX, ratioY);
                path.moveTo(ratioX, ((float) height) - ratioY);
                path.lineTo(ratioX, (float) height);
                path.lineTo(((float) width) - ratioX, (float) height);
                path.lineTo(((float) width) - ratioX, ((float) height) - ratioY);
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SQUARE_OUT("SQUARE_OUT") {
            public Bitmap getMask(int width, int height, int factor) {

                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                float ratioX = (((float) width) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) height) / (((float) FinalMaskBitmap.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                new Canvas(mask).drawRect(new RectF(((float) (width / 2)) - ratioX, ((float) (height / 2)) - ratioY, ((float) (width / 2)) + ratioX, ((float) (height / 2)) + ratioY), paint);
                return mask;
            }
        },
        VERTICAL_RECT("VERTICAL_RECT") {
            public Bitmap getMask(int width, int height, int factor) {

                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float hf = ((float) height) / 10.0f;
                float rectH = (((float) factor) * hf) / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL);
                for (int i = 0; i < 10; i++) {
                    canvas.drawRect(new Rect(0, (int) (((float) i) * hf), width, (int) ((((float) i) * hf) + rectH)), paint);
                }
                drawText(canvas);
                return mask;
            }
        },
        WIND_MILL("WIND_MILL") {
            public Bitmap getMask(int width, int height, int factor) {
                float r = FinalMaskBitmap.getRad(width, height);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                RectF oval = new RectF();
                oval.set((((float) width) / 2.0f) - r, (((float) height) / 2.0f) - r, (((float) width) / 2.0f) + r, (((float) height) / 2.0f) + r);
                float angle = (90.0f / ((float) FinalMaskBitmap.ANIMATED_FRAME_CAL)) * ((float) factor);
                canvas.drawArc(oval, 90.0f, angle, true, paint);
                canvas.drawArc(oval, 180.0f, angle, true, paint);
                canvas.drawArc(oval, 270.0f, angle, true, paint);
                canvas.drawArc(oval, 360.0f, angle, true, paint);
                drawText(canvas);
                return mask;
            }
        },
        Roll2D_TB("Roll2D_TB") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollWhole3D(obj, obj2, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        Roll2D_BT("Roll2D_BT") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollWhole3D(obj2, obj, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        Roll2D_LR("Roll2D_LR") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollWhole3D(obj, obj2, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        Roll2D_RL("Roll2D_RL") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollWhole3D(obj2, obj, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        Whole3D_TB("Whole3D_TB") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.drawRollWhole3D(obj, obj2, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.rollMode = this;
            }
        },
        Whole3D_BT("Whole3D_BT") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.drawRollWhole3D(obj2, obj, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        Whole3D_LR("Whole3D_LR") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.drawRollWhole3D(obj, obj2, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        Whole3D_RL("Whole3D_RL") {
            public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.drawRollWhole3D(obj2, obj, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
            }
        },
        SepartConbine_TB("SepartConbine_TB") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawSepartConbine(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 4;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap, bitmap2, this);
            }
        },
        SepartConbine_BT("SepartConbine_BT") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawSepartConbine(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 4;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap2, bitmap, this);
            }
        },
        SepartConbine_LR("SepartConbine_LR") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawSepartConbine(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 4;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap, bitmap2, this);
            }
        },
        SepartConbine_RL("SepartConbine_RL") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawSepartConbine(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 4;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap2, bitmap, this);
            }
        },
        RollInTurn_BT("RollInTurn_BT") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollInTurn(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap2, bitmap, this);
            }
        },
        RollInTurn_LR("RollInTurn_LR") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollInTurn(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap, bitmap2, this);
            }
        },
        RollInTurn_TB("RollInTurn_TB") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollInTurn(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap, bitmap2, this);
            }
        },
        RollInTurn_RL("RollInTurn_RL") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawRollInTurn(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap2, bitmap, this);
            }
        },
        Jalousie_BT("Jalousie_BT") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(FinalMaskBitmap.ANIMATED_FRAME_CAL - i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawJalousie(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 1;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap2, bitmap, this);
            }
        },
        Jalousie_LR("Jalousie_LR") {
            public Bitmap getMask(int obj, int obj2, int i) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.setRotateDegree(i);
                Bitmap createBitmap = Bitmap.createBitmap(MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight(), Config.ARGB_8888);
                FinalMaskBitmap.drawJalousie(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                FinalMaskBitmap.rollMode = this;
                FinalMaskBitmap.partNumber = 8;
                FinalMaskBitmap.direction = 0;
                FinalMaskBitmap.camera = new Camera();
                FinalMaskBitmap.matrix = new Matrix();
                FinalMaskBitmap.initBitmaps(bitmap, bitmap2, this);
            }
        };
        String name;

        public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
        }

        public Bitmap getMask(Bitmap obj, Bitmap obj2, int i) {
            return null;
        }


        public Bitmap getMask(int obj, int obj2, int i) {
            return null;
        }


        EFFECT(String name) {
            this.name = "";
            this.name = name;
        }

        @SuppressLint("RestrictedApi")
        public void drawText(Canvas canvas) {
            Paint paint = new Paint();
            paint.setTextSize(50.0f);
            paint.setColor(SupportMenu.CATEGORY_MASK);
        }
    }

    static {
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
    }


    public static void reintRect() {
        randRect = (int[][]) Array.newInstance(Integer.TYPE, new int[]{(int) ANIMATED_FRAME, (int) ANIMATED_FRAME});
        for (int[] ints : randRect) {
            Arrays.fill(ints, 0);
        }
    }

    static float getRad(int w, int h) {
        return (float) Math.sqrt((double) ((w * w) + (h * h)) / 4);
    }


    public static void initBitmaps(Bitmap bitmap, Bitmap bitmap2, EFFECT effect) {
        Bitmap bitmap3;
        rollMode = effect;
        if (MyApplication.getInstance().getVideoHeight() > 0 || MyApplication.getInstance().getVideoWidth() > 0) {
            bitmaps = (Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{2, partNumber});
            averageWidth = MyApplication.getInstance().getVideoWidth() / partNumber;
            averageHeight = MyApplication.getInstance().getVideoHeight() / partNumber;
            int i = 0;
            while (i < 2) {
                for (int i2 = 0; i2 < partNumber; i2++) {
                    if (rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR) {
                        if (direction == 1) {
                            bitmap3 = getPartBitmap(i == 0 ? bitmap : bitmap2, 0, averageHeight * i2, new Rect(0, averageHeight * i2, MyApplication.getInstance().getVideoWidth(), (i2 + 1) * averageHeight));
                        } else {
                            int i3 = averageWidth;
                            bitmap3 = getPartBitmap(i == 0 ? bitmap : bitmap2, averageWidth * i2, 0, new Rect(i3 * i2, 0, (i2 + 1) * i3, MyApplication.getInstance().getVideoHeight()));
                        }
                    } else if (direction == 1) {
                        int i4 = averageWidth;
                        bitmap3 = getPartBitmap(i == 0 ? bitmap : bitmap2, averageWidth * i2, 0, new Rect(i4 * i2, 0, (i2 + 1) * i4, MyApplication.getInstance().getVideoHeight()));
                    } else {
                        Bitmap bitmap4 = i == 0 ? bitmap : bitmap2;
                        int i5 = averageHeight;
                        bitmap3 = getPartBitmap(bitmap4, 0, i5 * i2, new Rect(0, i5 * i2, MyApplication.getInstance().getVideoWidth(), (i2 + 1) * averageHeight));
                    }
                    bitmaps[i][i2] = bitmap3;
                }
                i++;
            }
        }
    }

    private static Bitmap getPartBitmap(Bitmap bitmap, int i, int i2, Rect rect) {
        return Bitmap.createBitmap(bitmap, i, i2, rect.width(), rect.height());
    }


    public static void setRotateDegree(int i) {
        if (rollMode == EFFECT.RollInTurn_BT || rollMode == EFFECT.RollInTurn_LR || rollMode == EFFECT.RollInTurn_RL || rollMode == EFFECT.RollInTurn_TB) {
            rotateDegree = (((((float) (partNumber - 1)) * 30.0f) + 90.0f) * ((float) i)) / ((float) ANIMATED_FRAME_CAL);
        } else if (rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR) {
            rotateDegree = (((float) i) * 180.0f) / ((float) ANIMATED_FRAME_CAL);
        } else {
            rotateDegree = (((float) i) * 90.0f) / ((float) ANIMATED_FRAME_CAL);
        }
        int i2 = 180;
        float mRoteFactor;
        if (direction == 1) {
            mRoteFactor = rotateDegree;
            if (!(rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR)) {
                i2 = 90;
            }
            axisY = (mRoteFactor / ((float) i2)) * ((float) MyApplication.getInstance().getVideoHeight());
            return;
        }
        mRoteFactor = rotateDegree;
        if (!(rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR)) {
            i2 = 90;
        }
        axisX = (mRoteFactor / ((float) i2)) * ((float) MyApplication.getInstance().getVideoWidth());
    }

    public static void drawRollWhole3D(Bitmap bitmap, Bitmap bitmap2, Canvas canvas, boolean z) {
        canvas.save();
        if (direction == 1) {
            camera.save();
            if (z) {
                camera.rotateX(0.0f);
            } else {
                camera.rotateX(-rotateDegree);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-MyApplication.getInstance().getVideoWidth()) / 2), 0.0f);
            matrix.postTranslate((float) (MyApplication.getInstance().getVideoWidth() / 2), axisY);
            canvas.drawBitmap(bitmap, matrix, paint);
            camera.save();
            if (z) {
                camera.rotateX(0.0f);
            } else {
                camera.rotateX(90.0f - rotateDegree);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-MyApplication.getInstance().getVideoWidth()) / 2), (float) (-MyApplication.getInstance().getVideoHeight()));
            matrix.postTranslate((float) (MyApplication.getInstance().getVideoWidth() / 2), axisY);
            canvas.drawBitmap(bitmap2, matrix, paint);
        } else {
            camera.save();
            if (z) {
                camera.rotateY(0.0f);
            } else {
                camera.rotateY(rotateDegree);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(0.0f, (float) ((-MyApplication.getInstance().getVideoHeight()) / 2));
            matrix.postTranslate(axisX, (float) (MyApplication.getInstance().getVideoHeight() / 2));
            canvas.drawBitmap(bitmap, matrix, paint);
            camera.save();
            if (z) {
                camera.rotateY(0.0f);
            } else {
                camera.rotateY(rotateDegree - 90.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) (-MyApplication.getInstance().getVideoWidth()), (float) ((-MyApplication.getInstance().getVideoHeight()) / 2));
            matrix.postTranslate(axisX, (float) (MyApplication.getInstance().getVideoHeight() / 2));
            canvas.drawBitmap(bitmap2, matrix, paint);
        }
        canvas.restore();
    }

    public static void drawSepartConbine(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap[][] bitmapArr = bitmaps;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            canvas.save();
            if (direction == 1) {
                camera.save();
                camera.rotateX(-rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-bitmap.getWidth()) / 2), 0.0f);
                matrix.postTranslate((float) ((bitmap.getWidth() / 2) + (averageWidth * i)), axisY);
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
                camera.rotateX(90.0f - rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) (-bitmap2.getHeight()));
                matrix.postTranslate((float) ((bitmap2.getWidth() / 2) + (averageWidth * i)), axisY);
                canvas.drawBitmap(bitmap2, matrix, paint);
            } else {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate(0.0f, (float) ((-bitmap.getHeight()) / 2));
                matrix.postTranslate(axisX, (float) ((bitmap.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
                camera.rotateY(rotateDegree - 90.0f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-bitmap2.getWidth()), (float) ((-bitmap2.getHeight()) / 2));
                matrix.postTranslate(axisX, (float) ((bitmap2.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(bitmap2, matrix, paint);
            }
            canvas.restore();
        }
    }

    public static void drawRollInTurn(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap[][] bitmapArr = bitmaps;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            float f = rotateDegree - ((float) (i * 30));
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 90.0f) {
                f = 90.0f;
            }
            canvas.save();
            if (direction == 1) {
                float f2 = (f / 90.0f) * ((float) MyApplication.getInstance().getVideoHeight());
                if (f2 > ((float) MyApplication.getInstance().getVideoHeight())) {
                    f2 = (float) MyApplication.getInstance().getVideoHeight();
                }
                if (f2 < 0.0f) {
                    f2 = 0.0f;
                }
                camera.save();
                camera.rotateX(-f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-bitmap.getWidth()), 0.0f);
                matrix.postTranslate((float) (bitmap.getWidth() + (averageWidth * i)), f2);
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
                camera.rotateX(90.0f - f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-bitmap2.getWidth()), (float) (-bitmap2.getHeight()));
                matrix.postTranslate((float) (bitmap2.getWidth() + (averageWidth * i)), f2);
                canvas.drawBitmap(bitmap2, matrix, paint);
            } else {
                float f3 = (f / 90.0f) * ((float) MyApplication.getInstance().getVideoWidth());
                if (f3 > ((float) MyApplication.getInstance().getVideoWidth())) {
                    f3 = (float) MyApplication.getInstance().getVideoWidth();
                }
                if (f3 < 0.0f) {
                    f3 = 0.0f;
                }
                camera.save();
                camera.rotateY(f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate(0.0f, (float) ((-bitmap.getHeight()) / 2));
                matrix.postTranslate(f3, (float) ((bitmap.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
                camera.rotateY(f - 90.0f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-bitmap2.getWidth()), (float) ((-bitmap2.getHeight()) / 2));
                matrix.postTranslate(f3, (float) ((bitmap2.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(bitmap2, matrix, paint);
            }
            canvas.restore();
        }
    }

    public static void drawJalousie(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap[][] bitmapArr = bitmaps;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            canvas.save();
            if (direction == 1) {
                if (rotateDegree < 90.0f) {
                    camera.save();
                    camera.rotateX(rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();
                    matrix.preTranslate((float) ((-bitmap.getWidth()) / 2), (float) ((-bitmap.getHeight()) / 2));
                    matrix.postTranslate((float) (bitmap.getWidth() / 2), (float) ((bitmap.getHeight() / 2) + (averageHeight * i)));
                    canvas.drawBitmap(bitmap, matrix, paint);
                } else {
                    camera.save();
                    camera.rotateX(180.0f - rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();
                    matrix.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) ((-bitmap2.getHeight()) / 2));
                    matrix.postTranslate((float) (bitmap2.getWidth() / 2), (float) ((bitmap2.getHeight() / 2) + (averageHeight * i)));
                    canvas.drawBitmap(bitmap2, matrix, paint);
                }
            } else if (rotateDegree < 90.0f) {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-bitmap.getWidth()) / 2), (float) ((-bitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((bitmap.getWidth() / 2) + (averageWidth * i)), (float) (bitmap.getHeight() / 2));
                canvas.drawBitmap(bitmap, matrix, paint);
            } else {
                camera.save();
                camera.rotateY(180.0f - rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) ((-bitmap2.getHeight()) / 2));
                matrix.postTranslate((float) ((bitmap2.getWidth() / 2) + (averageWidth * i)), (float) (bitmap2.getHeight() / 2));
                canvas.drawBitmap(bitmap2, matrix, paint);
            }
            canvas.restore();
        }
    }

}
