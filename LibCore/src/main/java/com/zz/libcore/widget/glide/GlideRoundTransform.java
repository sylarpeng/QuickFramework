package com.zz.libcore.widget.glide;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/24 16:31
 * 类描述：
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Glide 圆角 Transform
 */

public class GlideRoundTransform extends BitmapTransformation {

    private final String ID = "com.zz.libcore.widget.glide";
    private static float RADIUS = 4.0f;
    /**
     * 是否四边都圆角，默认是上边圆角
     */
    private boolean isAllTransform;

    public GlideRoundTransform() {}

    public GlideRoundTransform(float radiusDp,boolean all){
        this.RADIUS=Resources.getSystem().getDisplayMetrics().density * radiusDp;
        this.isAllTransform=all;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if(toTransform == null){
            return null;
        }
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        if(result == null){
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF rectF = new RectF(0, 0, toTransform.getWidth(), toTransform.getHeight());
        canvas.drawRoundRect(rectF, RADIUS, RADIUS, paint);
        if(!isAllTransform){
            canvas.drawRect(0, rectF.bottom - RADIUS, RADIUS, rectF.bottom, paint);
            canvas.drawRect(rectF.right - RADIUS, rectF.bottom - RADIUS, rectF.right, rectF.bottom, paint);
        }

        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        try {
            byte[] idBytes = ID.getBytes(STRING_CHARSET_NAME);
            messageDigest.update(idBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof GlideRoundTransform;
    }
}
