package com.zz.libcore.widget.image;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;

import static android.view.View.NO_ID;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/25 9:22
 * 类描述：图片加载配置
 */
public class ImageConfig {
    public static final int CENTER_INSIDE = 0;
    public static final int CENTER_CROP = 1;
    public static final int FIT_CENTER = 2;

    @IntDef({CENTER_INSIDE, CENTER_CROP, FIT_CENTER})
    public @interface ScaleType { }

    /**
     * 裁剪类型,默认CENTER_INSIDE
     */
    public  @ScaleType int scaleType;
    /**
     * 加载中占位图
     */
    public @DrawableRes int placeHolderResId;
    public Drawable placeHolderDrawable;
    /**
     * 加载错误占位图
     */
    public @DrawableRes int errorHolderResId;
    public Drawable errorHolderDrawable;
    /**
     * 是否圆形裁剪
     */
    public boolean isCircleCrop;
    /**
     * 圆形边框颜色
     */
    public @ColorInt int circleBorderColor;
    /**
     * 圆形边框宽度
     */
    public float circleBorderWidth;
    /**
     * 是否圆角裁剪
     */
    public boolean isRoundCrop;
    /**
     * 圆角大小
     */
    public float roundRadius;

    /**
     * 是否高斯模糊
     */
    public boolean isBlur;

    /**
     * 旋转角度
     */
    public int rotationAngle;

    public ImageConfig(Builder builder) {
        this.scaleType=builder.scaleType;
        this.placeHolderResId=builder.placeHolderResId;
        this.placeHolderDrawable=builder.placeHolderDrawable;
        this.errorHolderResId=builder.errorHolderResId;
        this.errorHolderDrawable=builder.errorHolderDrawable;
        this.isCircleCrop=builder.isCircleCrop;
        this.circleBorderColor=builder.circleBorderColor;
        this.circleBorderWidth=builder.circleBorderWidth;
        this.isRoundCrop=builder.isRoundCrop;
        this.roundRadius=builder.roundRadius;
        this.isBlur=builder.isBlur;
        this.rotationAngle=builder.rotationAngle;
    }

    public static class Builder{
        private @ScaleType int scaleType=CENTER_INSIDE;
        private @DrawableRes int placeHolderResId=NO_ID;
        private Drawable placeHolderDrawable;
        private @DrawableRes int errorHolderResId=NO_ID;
        private Drawable errorHolderDrawable;
        private boolean isCircleCrop;
        private @ColorInt int circleBorderColor;
        private float circleBorderWidth;
        private boolean isRoundCrop;
        private float roundRadius;
        private boolean isBlur;
        private int rotationAngle;

        public Builder scaleType(@ScaleType int scaleType){
            this.scaleType=scaleType;
            return this;
        }
        public Builder placeHolderResId(@DrawableRes int placeHolderResId){
            this.placeHolderResId=placeHolderResId;
            return this;
        }
        public Builder placeHolderDrawable(Drawable placeHolderDrawable){
            this.placeHolderDrawable=placeHolderDrawable;
            return this;
        }
        public Builder errorHolderResId(@DrawableRes int errorHolderResId){
            this.errorHolderResId=errorHolderResId;
            return this;
        }
        public Builder errorHolderDrawable(Drawable errorHolderDrawable){
            this.errorHolderDrawable=errorHolderDrawable;
            return this;
        }
        public Builder isCircleCrop(boolean isCircleCrop){
            this.isCircleCrop=isCircleCrop;
            return this;
        }
        public Builder circleBorderColor(@ColorInt int circleBorderColor){
            this.circleBorderColor=circleBorderColor;
            return this;
        }
        public Builder circleBorderWidth(float circleBorderWidth){
            this.circleBorderWidth=circleBorderWidth;
            return this;
        }

        public Builder isRoundCrop(boolean isRoundCrop){
            this.isRoundCrop=isRoundCrop;
            return this;
        }
        public Builder roundRadius(float roundRadius){
            this.roundRadius=roundRadius;
            return this;
        }
        public Builder isBlur(boolean isBlur){
            this.isBlur=isBlur;
            return this;
        }
        public Builder rotationAngle(int rotationAngle){
            this.rotationAngle=rotationAngle;
            return this;
        }
        public ImageConfig bulid(){
            return new ImageConfig(this);
        }

    }
}
