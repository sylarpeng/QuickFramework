package com.zz.libcore.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.zz.libcore.R;
import com.zz.libcore.widget.glide.GlideImageLoader;

import java.math.BigDecimal;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/24 18:33
 * 类描述：
 */
public class ZImageView extends AppCompatImageView {
    private int scaleType;
    private Drawable placeDrawable;
    private Drawable errorDrawable;
    private boolean isCircle;
    private @ColorInt int circleBorderColor;
    private float circleBorderWidth;
    private boolean isRound;
    private float roundRadius;
    private boolean isBlur;
    private int rotationAngle;

    private ImageLoader imageLoader;
    private ImageConfig imageConfig;

    private float width, height;

    public ZImageView(@NonNull Context context) {
        this(context,null);
    }

    public ZImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ZImageView);
        if(attrs!=null){
            scaleType=ta.getInt(R.styleable.ZImageView_scaleType,0);
            placeDrawable=ta.getDrawable(R.styleable.ZImageView_placeHolder);
            errorDrawable=ta.getDrawable(R.styleable.ZImageView_errorHolder);
            isCircle=ta.getBoolean(R.styleable.ZImageView_isCircle,false);
            circleBorderColor=ta.getColor(R.styleable.ZImageView_circleBorderColor, Color.WHITE);
            circleBorderWidth=ta.getDimensionPixelSize(R.styleable.ZImageView_circleBorderWidth, 0);
            isRound=ta.getBoolean(R.styleable.ZImageView_isRound,false);
            roundRadius=ta.getDimensionPixelSize(R.styleable.ZImageView_roundRadius, 0);
            isBlur=ta.getBoolean(R.styleable.ZImageView_isBlur,false);
            rotationAngle=ta.getInt(R.styleable.ZImageView_rotationAngle, 0);
        }
        ta.recycle();
        imageLoader=GlideImageLoader.getInstance();
        imageConfig=new ImageConfig.Builder()
                .scaleType(scaleType)
                .placeHolderDrawable(placeDrawable)
                .errorHolderDrawable(errorDrawable)
                .isCircleCrop(isCircle)
                .circleBorderColor(circleBorderColor)
                .circleBorderWidth(circleBorderWidth)
                .isRoundCrop(isRound)
                .roundRadius(roundRadius)
                .isBlur(isBlur)
                .rotationAngle(rotationAngle)
                .bulid();
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    /**
     * 加载图片
     * @param url
     */
    public void setUrl(Object url){
        if(url==null || imageLoader==null) {
            return;
        }
        imageLoader.loadImage(this,url,imageConfig);
    }

    /**
     * 加载图片，设置view宽高
     * @param url
     * @param width
     * @param height
     */
    public void setUrl(Object url,int width,int height){
        setViewSize(width,height);
        setUrl(url);
    }

    /**
     * 加载图片，根据图片宽高比，设置view宽高
     * @param url
     * @param realWidth 图片实际展示宽度
     * @param srcWidth 图片原始宽度
     * @param srcHeight 图片原始高度
     */
    public void setUrl(Object url,int realWidth,int srcWidth,int srcHeight){
        setUrl(url,realWidth,getRealHeight(realWidth,srcWidth,srcHeight));
    }

    private int getRealHeight(int realWidth, int srcWidth, int srcHeight) {
        return new BigDecimal(String.valueOf(realWidth)).multiply(new BigDecimal(String.valueOf(srcHeight))).divide(new BigDecimal(String.valueOf(srcWidth)),2,BigDecimal.ROUND_HALF_UP).intValue();
    }

    private void setViewSize(int width, int height) {
        if(width<=0 || height<=0){
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        if(layoutParams==null){
            layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.width=width;
        layoutParams.height=height;
        this.setLayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.width=getWidth();
        this.height=getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDrawRound()) {
            float radius=imageConfig.roundRadius;
            Path path = new Path();
            //四个圆角
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.quadTo(width, 0, width, radius);
            path.lineTo(width, height - radius);
            path.quadTo(width, height, width - radius, height);
            path.lineTo(radius, height);
            path.quadTo(0, height, 0, height - radius);
            path.lineTo(0, radius);
            path.quadTo(0, 0, radius, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

    private boolean isDrawRound(){
        return imageConfig!=null
                && imageConfig.isRoundCrop
                && imageConfig.roundRadius>0
                && width>imageConfig.roundRadius
                && height>imageConfig.roundRadius;
    }
}
