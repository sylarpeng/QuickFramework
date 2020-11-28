package com.zz.quickframework

import android.content.Context
import android.view.View
import android.widget.TextView
import com.zz.libcore.widget.pullrefresh.PullRefreshView
import com.zz.libcore.widget.pullrefresh.RefreshCallBack
import com.zz.libcore.widget.pullrefresh.RefreshStateChangeListener
import com.zz.myapplication1.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/28 10:31
 * 类描述：下拉刷新/上拉加载更多控制器
 */
class RefreshController() {
    var refreshCallBack:RefreshCallBack?=null
    var pullRefreshView: PullRefreshView?=null


    fun stopRefresh(){
        pullRefreshView?.onRefreshCompleted()
    }
    /**
     * 自定义下拉视图
     */
    fun getCustomRefreshView(context:Context?): View? {
        return View.inflate(context, R.layout.refresh_rv_header,null)
    }

    /**
     * 动态刷新下拉视图
     */
    fun getRefreshCallBack(): RefreshStateChangeListener? {
        return object :
            RefreshStateChangeListener {
            override fun onPullDown(view: View, dy: Int, totalDy: Int) {
                view.findViewById<TextView>(R.id.tv_name).text="松开刷新"
            }
            override fun onRefresh(view: View) {
                view.findViewById<TextView>(R.id.tv_name).text="刷新中..."
                refreshCallBack?.onRefresh()
            }
            override fun onRefreshComplete(view: View) {
                view.findViewById<TextView>(R.id.tv_name).text="刷新完成"
            }
            override fun onRefreshNone() {}
        }
    }

}