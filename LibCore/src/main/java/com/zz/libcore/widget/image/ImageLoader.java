package com.zz.libcore.widget.image;

import android.widget.ImageView;

import androidx.annotation.IntDef;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/24 18:09
 * 类描述：图片加载
 */
public interface ImageLoader {

    /**
     * 加载图片
     * @param imageView
     * @param urlObj
     * @param config
     */
    void loadImage(ImageView imageView,Object urlObj,ImageConfig config);

}
