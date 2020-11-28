package com.zz.quickframework

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zz.libcore.utils.DensityUtil
import com.zz.libcore.widget.glide.GlideBlurTransformation
import com.zz.libcore.widget.glide.GlideCircleTransform
import com.zz.libcore.widget.image.ZImageView
import com.zz.libcore.widget.pullrefresh.PullRefreshView
import com.zz.myapplication1.R


/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 18:44
 * 类描述：
 */
class Fragment4 : RgRefreshFragment() {
    var refreshView: PullRefreshView?=null
    override fun getLayoutResId(): Int {
        return R.layout.activity_main4
    }

    override fun getPullRefreshView(): PullRefreshView? {
        return refreshView
    }
    override fun initView(view: View) {
        refreshView=view.findViewById(R.id.refresh_view)
        showLoadingView()

        var empty=view.findViewById<TextView>(R.id.tv_empty)
        empty.setOnClickListener(View.OnClickListener {
            showEmptyView()
            reset(empty)
        })

        view.findViewById<TextView>(R.id.tv_error).setOnClickListener(View.OnClickListener {
            showErrorView()
            reset(empty)
        })
        reset(empty)
        initImage(view)
    }

    override fun onRefresh() {
        refreshView?.postDelayed(Runnable {
            stopRefresh()
        },2000)
    }



    private fun initImage(view: View) {
        var url="https://uidesign.rglcdn.com/RG/image/4152/en_1920x600.jpg?impolicy=high";
        var gif="https://uidesign.rglcdn.com/RG/image/4887/APP-logo.gif";

        var iv: ZImageView =view.findViewById(R.id.iv)
        var iv1: ZImageView =view.findViewById(R.id.iv1)
        var iv2: ZImageView =view.findViewById(R.id.iv2)
        var iv3: ZImageView =view.findViewById(R.id.iv3)
        var iv4: ZImageView =view.findViewById(R.id.iv4)
        var iv5: ZImageView =view.findViewById(R.id.iv5)
        var iv6: ZImageView =view.findViewById(R.id.iv6)
        var iv7: ZImageView =view.findViewById(R.id.iv7)
        var iv8: ZImageView =view.findViewById(R.id.iv8)
        var iv9: ZImageView =view.findViewById(R.id.iv9)

        iv.setUrl(url,DensityUtil.getScreenWidth(mContext),5,1)
        iv1.setUrl(url)
        iv2.setUrl(url)
        iv3.setUrl(url)
        iv4.setUrl(url)
        iv5.setUrl(url)
        iv6.setUrl(url)

        iv7.setUrl(R.mipmap.ic_launcher,300,300)
        iv8.setUrl(R.mipmap.app_logo)
        iv9.setUrl(R.mipmap.app_logo)






    }

    private fun loadRoundImage(imageView: ImageView,url:Any,radius:Int){
        loadImage(imageView,url,RoundedCorners(radius))
    }

    private fun loadCircleImage(imageView: ImageView,url:Any){
        loadImage(imageView,url,GlideCircleTransform(1f, Color.RED))
    }

    private fun loadBlurImage(imageView: ImageView,url:Any){
        loadImage(imageView,url,GlideBlurTransformation())
    }

    private fun loadImage(imageView: ImageView,url:Any,transformation:Transformation<Bitmap>?){
        var requestBuilder=Glide.with(imageView.context).asDrawable().load(url)
        var options=RequestOptions()
        var multiplatform = if(transformation==null){
            MultiTransformation(CenterInside())
        }else{
            MultiTransformation(transformation,CenterInside())
        }
        options.optionalTransform(multiplatform)
        options.optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(multiplatform))
        requestBuilder.apply(options).into(imageView)

    }

    fun reset(view: View){
        view.postDelayed(Runnable {
            showContentView()
        },1000)
    }
}