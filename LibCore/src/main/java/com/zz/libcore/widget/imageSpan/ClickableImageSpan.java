package com.zz.libcore.widget.imageSpan;

import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pengzhijun
 * 创建时间:2019/9/10 13:55
 * 类描述：可点击的imageSpan
 */
public abstract class ClickableImageSpan extends ImageSpan {
    public ClickableImageSpan(Drawable b) {
        super(b);
    }

    public abstract void onClick(View view);
}