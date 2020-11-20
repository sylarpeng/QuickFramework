package com.zz.libnetwork.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zz.libnetwork.gson.CustomGsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/22 15:33
 * 类描述：Retrofit 管理类
 */
class RetrofitClient private constructor(){
    private val retrofitMap:ConcurrentHashMap<String,Retrofit> = ConcurrentHashMap()
    private var host:String?=null
    private var okhttpClient: OkHttpClient?=null
    private var converterFactory:Converter.Factory?=null
    private var callFactory : CallAdapter.Factory?=null

    companion object{
        private var instance:RetrofitClient?=null
        get() {
            if(field==null){
                field= RetrofitClient()
            }
            return field
        }
        @Synchronized
        fun get():RetrofitClient{
            return instance!!
        }
    }

    fun host(host:String):RetrofitClient{
        this.host=host
        return this
    }
    fun client(okhttpClient:OkHttpClient):RetrofitClient{
        this.okhttpClient=okhttpClient
        return this
    }
    fun converterFactory(converterFactory: Converter.Factory):RetrofitClient{
        this.converterFactory=converterFactory
        return this
    }

    fun callAdapterFactory(callFactory: CallAdapter.Factory):RetrofitClient{
        this.callFactory=callFactory
        return this
    }

    fun <T> createApiService(service:Class<T>): T {
        okhttpClient=if(okhttpClient==null) OkHttpClient.Builder().build() else okhttpClient
        converterFactory=if(converterFactory==null) CustomGsonConverterFactory.create() else converterFactory;
        callFactory=if(callFactory==null) CoroutineCallAdapterFactory() else callFactory;
        return createRetrofit(host!!,okhttpClient!!,converterFactory!!,callFactory!!).create(service)
    }

    private fun createRetrofit(host:String,okHttpClient:OkHttpClient,factory: Converter.Factory,callFactory:CallAdapter.Factory): Retrofit {
        if(!retrofitMap.containsKey(host)){
            var retrofit=Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(host)
                .addCallAdapterFactory(callFactory)
                .addConverterFactory(factory)
                .build()
            retrofitMap[host] = retrofit
        }
        return retrofitMap[host]!!
    }
}