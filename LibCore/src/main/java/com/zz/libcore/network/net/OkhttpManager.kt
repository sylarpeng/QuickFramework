package com.zz.libcore.network.net

import android.content.Context
import com.zz.libcore.network.cookie.CookieInfo
import com.zz.libcore.network.cookie.SimpleCookieJar
import com.zz.libcore.network.interceptors.HeaderInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/22 16:49
 * 类描述：okhttpclient管理
 */
class OkhttpManager private constructor(){
    private val defaultTimeOut:Long=60
    companion object{
        private var instance: OkhttpManager?=null
        get() {
            if(field==null){
                field= OkhttpManager()
            }
            return field
        }

        @Synchronized
        fun get(): OkhttpManager {
            return instance!!
        }
    }

    inner class Builder{
        internal var context: Context?=null
        internal var connectTimeOut:Long=defaultTimeOut
        internal var redTimeOut:Long=defaultTimeOut
        internal var writeTimeOut:Long=defaultTimeOut
        internal var interceptors:MutableList<Interceptor>?= null
        internal var cookies:MutableList<CookieInfo>?=null

        fun withContext(ctx:Context): Builder {
            this.context=ctx
            return this
        }
        fun connectTimeOut(time:Long): Builder {
            this.connectTimeOut=time
            return this
        }
        fun readTimeOut(time:Long): Builder {
            this.redTimeOut=time
            return this
        }
        fun writeTimeOut(time:Long): Builder {
            this.writeTimeOut=time
            return this
        }
        fun addInterceptor(interceptor:Interceptor): Builder {
            if(interceptors==null){
                interceptors = mutableListOf()
            }
            interceptors?.add(interceptor)
            return this
        }
        fun addCookie (domain:String,name:String,value:String): Builder {
            var cookieInfo= CookieInfo(domain,name,value)
            if(cookies==null){
                cookies = mutableListOf()
            }
            cookies?.add(cookieInfo)
            return this
        }

        fun build():OkHttpClient{
            return createOkhttpClient(this)
        }
    }

    fun createOkhttpClient(b: Builder):OkHttpClient{
        var okhttpBuilder:OkHttpClient.Builder=OkHttpClient().newBuilder()
        if(b.connectTimeOut>0){
            okhttpBuilder.connectTimeout(b.connectTimeOut,TimeUnit.SECONDS)
        }
        if(b.redTimeOut>0){
            okhttpBuilder.readTimeout(b.redTimeOut,TimeUnit.SECONDS)
        }
        if(b.writeTimeOut>0){
            okhttpBuilder.writeTimeout(b.writeTimeOut,TimeUnit.SECONDS)
        }
        if(b.cookies!=null){
            okhttpBuilder.cookieJar(SimpleCookieJar(b.context,b.cookies))
        }
        okhttpBuilder.addInterceptor(HeaderInterceptor())
        if(b.interceptors!=null && b.interceptors!!.isNotEmpty()){
            for(interceptor:Interceptor in b.interceptors!!){
                okhttpBuilder.addInterceptor(interceptor)
            }
        }

        return okhttpBuilder.build()
    }

}