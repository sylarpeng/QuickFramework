package com.zz.quickframework

import android.util.Log
import okhttp3.*
import okio.BufferedSink
import org.json.JSONObject
import java.lang.Exception
import java.nio.Buffer

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 18:03
 * 类描述：
 */
class LogInterceptor : Interceptor {
    private val jsonType:MediaType?= MediaType.parse("application/json; charset=utf-8")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response:Response= chain.proceed(request)
        val url = request.url()
        val requestBody = request.body()
        val responseBody = response.body()
        var result=getResult(responseBody)
        Log.d("dd","请求路径=${url.url()}")
        Log.d("dd","请求参数=${getRequestBody(requestBody)}")
        Log.d("dd","请求Headers=${getRequestHeader(request.headers())}")
        Log.d("dd","返回结果=${result}")
        return response.newBuilder().body(ResponseBody.create(jsonType,result)).build()
    }

    private fun getRequestBody(body:RequestBody?):String{
        var builder=StringBuilder()
        if(body is RequestBody){
            builder.append(body2String(body))
        }else if(body is FormBody){
            var size=body.size()
            if(size>0){
                for (index in 0 until size){
                    builder.append(body.name(index)+"="+body.value(index))
                    if(index<size-1){
                        builder.append("&")
                    }

                }
            }
        }
        return builder.toString()
    }

    private fun getResult(body:ResponseBody?):String{
        if(body!=null){
            return body.string()
        }
        return ""

    }


    private fun getRequestHeader(headers:Headers):String{
        var builder=JSONObject();
        if(headers.size()>0){
            for (index in 0 until headers.size()){
                builder.put(headers.name(index),headers.value(index))
            }
        }
        return builder.toString()
    }

    private fun body2String(body:RequestBody):String{
        var buffer=okio.Buffer()
        try {
            body.writeTo(buffer)
            return buffer.readUtf8()
        }catch (e:Exception){
            e.printStackTrace()
            return ""
        }
    }
}