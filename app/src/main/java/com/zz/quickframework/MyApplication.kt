package com.zz.quickframework

import android.app.Application
import android.content.Context
import android.content.res.Configuration

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 18:14
 * 类描述：
 */
class MyApplication : Application() {
    companion object{
        var context:Application?=null
        fun getContext():Context{
            return context!!
        }
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(LanuageUtil.changeAppLanguage(base))
//    }

    override fun onCreate() {
        super.onCreate()
        context=this
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}