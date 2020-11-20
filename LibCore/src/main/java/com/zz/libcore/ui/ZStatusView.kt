package com.zz.libcore.ui

import android.content.Context
import android.view.View
import com.zz.libcore.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 18:30
 * 类描述：默认状态view
 */
class ZStatusView(context:Context?) : IStatusView {
    var context:Context?=null
    init {
        this.context=context
    }
    override fun getLoadingView(): View {
        return View.inflate(context, R.layout.status_view_loading,null)
    }

    override fun getEmptyView(): View {
        return View.inflate(context, R.layout.status_view_empty,null)
    }

    override fun getErrorView(): View {
        return View.inflate(context, R.layout.status_view_error,null)
    }
}