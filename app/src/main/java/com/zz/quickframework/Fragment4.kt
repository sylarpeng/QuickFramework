package com.zz.quickframework

import android.view.View
import android.widget.TextView
import com.zz.libcore.ui.ZBaseFragment
import com.zz.myapplication1.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 18:44
 * 类描述：
 */
class Fragment4 : ZBaseFragment() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_main4
    }

    override fun initView(view: View) {
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
    }
    fun reset(view: View){
        view.postDelayed(Runnable {
            showContentView()
        },3000)
    }
}