package com.zz.libcore.ui

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zz.libcore.R
import com.zz.libcore.utils.StatusBarUtil


/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 18:15
 * 类描述：基类Activity
 */
open class ZBaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRootView()
        fixdOrientation()
        setStatusBar()
        log()
    }

    private fun setRootView() {
        var layoutResId=getLayoutResId()
        if(layoutResId>0){
            setContentView(layoutResId)
        }else{
            setContentView(R.layout.activity_base)
            var fragment=getFragment()
            if(fragment!=null){
                supportFragmentManager.beginTransaction().replace(R.id.fl_root_container,fragment,"").commit()
            }
        }
    }
    /**
     * 设置沉浸式状态栏
     */
    private fun setStatusBar() {
        //实现沉浸式状态栏
        if (isSupportStatusBar()) {
            StatusBarUtil.compat(this, ContextCompat.getColor(this, getStatusbarColor()), fitsSystemWindows()
            )
        }
    }

    @LayoutRes
    protected open fun getLayoutResId():Int{
        return 0
    }

    protected open fun getFragment(): Fragment?=null
    /**
     * fitsSystemWindows =true 时 布局或View预留出系统状态栏底部导航栏空间
     *
     */
    protected open fun fitsSystemWindows(): Boolean=true

    protected open fun getStatusbarColor(): Int =R.color.status_bar_default_color

    private fun isSupportStatusBar(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    private fun log() {
        var tag=this.componentName.className
        Log.d("onCreate",tag)
    }

    /**
     * 设置竖屏
     */
    private fun fixdOrientation() {
        if(isFixScreenOrientationPortrait()){
            requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * 是否固定竖屏
     */
    fun isFixScreenOrientationPortrait():Boolean=true

}