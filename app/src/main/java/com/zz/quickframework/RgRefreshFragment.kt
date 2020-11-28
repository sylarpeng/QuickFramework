package com.zz.quickframework

import android.os.Bundle
import android.view.View
import com.zz.libcore.ui.ZBaseFragment
import com.zz.libcore.widget.pullrefresh.PullRefreshView
import com.zz.libcore.widget.pullrefresh.RefreshCallBack

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/27 16:52
 * 类描述：带下拉刷新的fragment
 */
open class RgRefreshFragment :  ZBaseFragment() {
    var controller:RefreshController?=null;

    override fun initData() {
        controller=RefreshController()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRefreshView()
    }
    private fun initRefreshView() {
        var pullRefreshView = getPullRefreshView();
        controller?.pullRefreshView=pullRefreshView;
        pullRefreshView?.setCustomRefreshView(controller?.getCustomRefreshView(mContext))
        pullRefreshView?.setRefreshCallBack(controller?.getRefreshCallBack())
        controller?.refreshCallBack=object : RefreshCallBack{
            override fun onLoadMoreData() {
                this@RgRefreshFragment.onLoadMoreData()
            }
            override fun onRefresh() {
                this@RgRefreshFragment.onRefresh()
            }
        }
    }

    open fun getPullRefreshView():PullRefreshView?=null

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