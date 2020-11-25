package com.zz.libcore.widget.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zz.libcore.widget.image.ImageConfig;
import com.zz.libcore.widget.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/24 18:19
 * 类描述：glide 图片加载
 */
public class GlideImageLoader implements ImageLoader {
    public static ImageConfig defaultConfig=new ImageConfig.Builder()
            .bulid();

    private static volatile GlideImageLoader instance = null;

    private GlideImageLoader(){}

    public static GlideImageLoader getInstance() {
        if (instance == null) {
            synchronized (GlideImageLoader.class) {
                if (instance == null) {
                    instance = new GlideImageLoader();
                }
            }
    }
        return instance;
    }

    @Override
    public void loadImage(ImageView imageView, Object urlObj, ImageConfig config) {
        try {
            RequestBuilder<Drawable> builder = Glide.with(imageView.getContext()).asDrawable().load(urlObj);
            RequestOptions options = new RequestOptions();
            if(config==null){
                config=defaultConfig;
            }
            if(config.placeHolderResId!= View.NO_ID){
                options.placeholder(config.placeHolderResId);
            }
            if(config.placeHolderDrawable!= null){
                options.placeholder(config.placeHolderDrawable);
            }
            if(config.errorHolderResId!= View.NO_ID){
                options.placeholder(config.errorHolderResId);
            }
            if(config.errorHolderDrawable!= null){
                options.placeholder(config.errorHolderDrawable);
            }

            Transformation[] transformations=getTransformations(config);
            if(transformations!=null){
                MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(transformations);
                options.optionalTransform(multiTransformation);
                options.optionalTransform(WebpDrawable.class,new WebpDrawableTransformation(multiTransformation));
            }
            options.format(DecodeFormat.PREFER_ARGB_8888);
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.priority(Priority.HIGH);
            builder.apply(options).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Transformation[] getTransformations(ImageConfig config) {
        List<Transformation<Bitmap>> transformationList=new ArrayList<>();
        if(config.isCircleCrop){
            transformationList.add(new GlideCircleTransform(config.circleBorderWidth,config.circleBorderColor));
        }else if(config.isRoundCrop){
            transformationList.add(new RoundedCorners((int)config.roundRadius));
        }
        if(config.rotationAngle!=0){
            transformationList.add(new GlideRotateTransformation(config.rotationAngle));
        }
        if(config.isBlur){
            transformationList.add(new GlideBlurTransformation());
        }
        transformationList.add(getScaleTransformation(config.scaleType));
        Transformation[] transformations=transformationList.toArray(new Transformation[transformationList.size()]);
        return transformations;
    }

    private Transformation<Bitmap> getScaleTransformation(@ImageConfig.ScaleType int scaleType){
        Transformation<Bitmap> transformation;
        switch (scaleType){
            case ImageConfig.CENTER_INSIDE:
                transformation=new CenterInside();
                break;
            case ImageConfig.FIT_CENTER:
                transformation=new FitCenter();
                break;
            default:
                transformation=new CenterCrop();
                break;
        }
        return transformation;
    }
}
