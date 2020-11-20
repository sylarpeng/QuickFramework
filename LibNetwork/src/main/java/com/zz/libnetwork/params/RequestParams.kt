package com.zz.libnetwork.params

import com.google.gson.Gson
import com.zz.libnetwork.interceptors.HeaderInterceptor
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.Serializable
import java.util.concurrent.ConcurrentHashMap

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 10:48
 * 类描述：基础body,header参数
 */
open class RequestParams :Serializable {
    private val jsonType:MediaType = MediaType.parse("application/json; charset=utf-8")!!
    private val formType:MediaType= MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")!!
    var headers:ConcurrentHashMap<String,String> =ConcurrentHashMap()
    var urlParams:ConcurrentHashMap<String,Any> = ConcurrentHashMap()
    var isJsonParams:Boolean=true

    fun addHeader(key:String,value:String){
        if(!headers.containsKey(key)){
            headers[key]=value
        }
    }

    fun addParam(key:String,value:Any){
        if(!urlParams.containsKey(key)){
            urlParams[key]=value
        }
    }

    /**
     * 添加headers信息到body
     */
    fun createRequestBody():RequestBody{
        return if(isJsonParams){
            var jsonBody=Gson().toJson(urlParams)
            JsonRequestBody.create(jsonType,jsonBody).setHeaders(headers)
        }else{
            val bodyBuilder = FormBody.Builder()
            //添加body参数
            if(urlParams.size>0){
                urlParams.forEach{
                    bodyBuilder.addEncoded(it.key, it.value.toString())
                }
            }
            //添加header
            if(headers.size>0){
                headers.forEach{
                    bodyBuilder.addEncoded(HeaderInterceptor.headerKey+it.key, it.value)
                }
            }
            bodyBuilder.build()
        }
    }

}