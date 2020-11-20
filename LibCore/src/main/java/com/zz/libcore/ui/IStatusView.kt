package com.zz.libcore.ui

import android.view.View

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 16:38
 * 类描述：
 */
interface IStatusView {
    fun getLoadingView(): View
    fun getEmptyView(): View
    fun getErrorView(): View
}