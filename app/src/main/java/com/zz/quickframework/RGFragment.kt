package com.zz.quickframework

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.zz.libcore.ui.IStatusView
import com.zz.libcore.ui.ZBaseFragment

/**
 * 项目名称:Dresslily-Android
 * 创建人：pzj
 * 创建时间:2/7/21 5:10 PM
 * 类描述：实现ViewBinding 和 自定义状态布局(加载,空样式,网络异常...)
 */
open class RGFragment<T: ViewBinding>(layResId:Int) :ZBaseFragment(layResId) {
    protected lateinit var bind:T
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentView?.let {
            var b=bindView(it)
            b?.let {
                bind=b
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * viewbinding
     */
    protected open fun bindView(contentView:View):T?=null

}