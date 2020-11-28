package com.zz.quickframework

import com.zz.libnetwork.gson.CustomGsonConverterFactory
import com.zz.libnetwork.net.OkhttpManager
import com.zz.libnetwork.net.RetrofitClient
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/22 17:11
 * 类描述：
 */
class ApiManager {
    companion object{
        val servicesMap: ConcurrentHashMap<String, Any> = ConcurrentHashMap()
        var host="https://jsonplaceholder.typicode.com/";
        var msgHost="http://www.uos.com.develop.php7.egomsl.com"

        var okHttpClient=OkhttpManager.get().Builder()
            .withContext(MyApplication.getContext())
            .connectTimeOut(60)
            .readTimeOut(60)
            .writeTimeOut(60)
            .addCookie("jsonplaceholder.typicode.com","stageing","true")
            .addCookie("jsonplaceholder.typicode.com","cookieTest","text1111")
            .addInterceptor(EncryptionInterceptor())
            .addInterceptor(LogInterceptor())
            .build()
        fun testApi():TestApi{
            return RetrofitClient.get()
                .host(host)
                .client(okHttpClient)
                .createApiService(TestApi::class.java)
        }

        fun msgApi():MessageApi{
            return RetrofitClient.get()
                .host(msgHost)
                .client(okHttpClient)
                .converterFactory(CustomGsonConverterFactory.create())
                .createApiService(MessageApi::class.java)
        }


    }
}