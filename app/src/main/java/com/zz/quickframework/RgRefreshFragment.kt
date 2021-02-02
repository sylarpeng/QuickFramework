package com.zz.quickframework

import android.view.View
import androidx.core.content.ContextCompat
import com.zz.libcore.ui.ZBaseFragment
import com.zz.libcore.widget.pullrefresh.PullRefreshView
import com.zz.libcore.widget.pullrefresh.RefreshCallBack
import com.zz.myapplication1.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/27 16:52
 * 类描述：带下拉刷新的fragment
 */
open class RgRefreshFragment(layResId:Int) :  ZBaseFragment(layResId) {
    var controller:RefreshController?=null
    override fun getLayoutView(): View? {
        var rootView=View.inflate(mContext, R.layout.refresh_rv_content, null)
        initRefreshView(rootView.findViewById(R.id.refresh_view))
        return rootView;
    }

    /**
     * 初始化PullRefreshView
     */
    private fun initRefreshView(pullRefreshView: PullRefreshView?) {
        controller=RefreshController()
        this.bindingView=View.inflate(mContext, contentLayoutResId, null)
        pullRefreshView?.run {
            setSwipeRefreshEnable(true, ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setHeaderView(controller?.getCustomRefreshView(mContext))
                .setContentView(bindingView)
                .setRefreshCallBack(controller?.getRefreshCallBack())
                .create()

        }
        controller?.pullRefreshView=pullRefreshView;
        controller?.refreshCallBack=object : RefreshCallBack{
            override fun onLoadMoreData() {
                this@RgRefreshFragment.onLoadMoreData()
            }
            override fun onRefresh() {
                this@RgRefreshFragment.onRefresh()
            }
        }
    }

    /**
     * 下拉刷新操作
     */
    open fun onRefresh(){

    }

    /**
     * 加载更多
     */
    open fun onLoadMoreData(){

    }

    /**
     * 刷新完成
     */
    fun stopRefresh(){
        controller?.stopRefresh()
    }
}