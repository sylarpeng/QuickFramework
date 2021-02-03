package com.zz.libcore.utils

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import java.lang.Exception

object AppUtil {
    /**
     * 获取app版本号
     */
    fun getVersionCode(context: Context):Long{
        val packageManager = context.packageManager
        return try {
            var info=packageManager.getPackageInfo(context.packageName,0)
            PackageInfoCompat.getLongVersionCode(info)
        } catch (e:Exception) {
            e.printStackTrace()
            0
        }
    }
    /**
     * 获取app版本号
     */
    fun getVersionName(context: Context):String{
        val packageManager = context.packageManager
        return try {
            var info=packageManager.getPackageInfo(context.packageName,0)
            info.versionName
        } catch (e:Exception) {
            e.printStackTrace()
            "1.0"
        }
    }

}