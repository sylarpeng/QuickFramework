package com.zz.quickframework

import com.zz.libcore.ui.ZBaseActivity

open class ViewBindingActivity(layoutRes:Int):ZBaseActivity() {
    private var layResId:Int=0;
    init {
        this.layResId=layoutRes;
    }

    override fun getLayoutResId(): Int {
        return layResId;
    }
}