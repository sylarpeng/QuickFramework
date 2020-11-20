package com.zz.libcore.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by  : pzj
 * Created date:2018/3/21
 * description : 沉浸式状态栏管理
 */

public class StatusBarUtil {
    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.WHITE;



    public static void compat(Activity activity)
    {
        compat(activity, COLOR_DEFAULT,true);
    }


    public static void compat(Activity activity, int statusColor, boolean fitsSystemWindows)
    {
        setFitsSystemWindows(activity,fitsSystemWindows);
        setStatusBarFontColor(activity,fitsSystemWindows?statusColor: Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().setStatusBarColor(fitsSystemWindows?statusColor: Color.TRANSPARENT);
        }else{
            ViewGroup contentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    fitsSystemWindows?getStatusBarHeight(activity):0);
            statusBarView.setBackgroundColor(statusColor);
            contentView.addView(statusBarView, lp);
        }

    }

    /**
     * 设置状态栏文字颜色（白色与透明,避免白色底状态栏时与文字颜色一致）
     */
    public static void setStatusBarFontColor(Activity activity, int statusColor){
        if ((statusColor == Color.WHITE || statusColor == Color.TRANSPARENT)  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 部分机型的statusbar会有半透明的黑色背景
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            if (SystemUtils.isMIUI()) {
                SystemUtils.miUISetStatusBarLightMode(activity.getWindow(), true);
            }

            if (SystemUtils.isMEIZU()) {
                SystemUtils.flymeSetStatusBarLightMode(activity.getWindow(), true);
            }
        }
    }

    private static void setFitsSystemWindows(Activity activity, boolean fitsSystemWindows){
        ViewGroup contentFrameLayout = activity.findViewById(Window.ID_ANDROID_CONTENT);
        if(contentFrameLayout!=null && contentFrameLayout.getChildCount()>0){
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null) {
                parentView.setFitsSystemWindows(fitsSystemWindows);
            }
        }
    }



    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
