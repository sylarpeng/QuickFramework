package com.zz.libcore.widget.glide;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pengzhijun
 * 创建时间:2020/3/9 13:46
 * 类描述：高斯模糊
 */
public class GlideBlurTransformation extends BitmapTransformation {
    private static final int VERSION = 1;
    private static final String ID = "com.globalegrow.glideview.glide.BlurTransformation." + VERSION;

    private static int MAX_RADIUS = 20;
    private static int DEFAULT_DOWN_SAMPLING = 1;
    /**
     * 模糊半径
     */
    private int fuzzyRadius;
    /**
     * 采样
     */
    private int sampling;

    public GlideBlurTransformation() {
        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;
        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);
        bitmap = FastBlur.blur(bitmap, fuzzyRadius, true);
        return bitmap;
    }

    public GlideBlurTransformation(int radius) {
        this(radius, DEFAULT_DOWN_SAMPLING);
    }

    public GlideBlurTransformation(int radius, int sampling) {
        this.fuzzyRadius = radius == 0 ? MAX_RADIUS : radius;
        this.sampling = sampling == 0 ? DEFAULT_DOWN_SAMPLING : sampling;
    }

    @Override
    public String toString() {
        return "BlurTransformation(fuzzyRadius=" + fuzzyRadius + ", sampling=" + sampling + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideBlurTransformation &&
            ((GlideBlurTransformation) o).fuzzyRadius == fuzzyRadius &&
            ((GlideBlurTransformation) o).sampling == sampling;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + fuzzyRadius * 1000 + sampling * 10;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + fuzzyRadius + sampling).getBytes(CHARSET));
    }
}
