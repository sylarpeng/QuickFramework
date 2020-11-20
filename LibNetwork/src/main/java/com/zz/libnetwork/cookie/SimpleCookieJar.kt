package com.zz.libnetwork.cookie

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.lang.Exception

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 16:43
 * 类描述：自定义cookie jar
 */
class SimpleCookieJar(context: Context?,cookies:MutableList<CookieInfo>?) : PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context)) {
    var cookiesList:MutableList<CookieInfo>?=null
    init {
        cookiesList=cookies
    }
    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        var newCookies:MutableList<Cookie> = super.loadForRequest(url)
        try {
            if(cookiesList!=null && cookiesList!!.isNotEmpty()){
                for(c:CookieInfo in cookiesList!!){
                    newCookies.add(createCookie(c))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

        return newCookies
    }

    fun createCookie(cookieInfo:CookieInfo):Cookie{
        return Cookie.Builder()
            .hostOnlyDomain(cookieInfo.domain)
            .path("/")
            .name(cookieInfo.name)
            .value(cookieInfo.value)
            .build()
    }

}