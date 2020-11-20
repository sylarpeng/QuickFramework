package com.zz.quickframework;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 8:45
 * 类描述：
 */
public class LanuageUtil {

    /**
     * 修改app的语言
     */
    public static Context changeAppLanguage(Context context) {
        try {
            return changeAppLanguageConfig(context);
        }catch (Exception e){
            return context;
        }
    }
    private static Context changeAppLanguageConfig(Context context) {
        try {
            String language = "fr";
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.createConfigurationContext(configuration);
            }
            Resources resources = context.getResources();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}
