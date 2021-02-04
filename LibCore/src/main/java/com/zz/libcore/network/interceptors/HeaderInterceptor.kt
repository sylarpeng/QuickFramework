package com.zz.libcore.network.interceptors

import com.zz.libcore.network.params.JsonRequestBody
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 13:59
 * 类描述：通过拦截器添加header
 */
class HeaderInterceptor : Interceptor {
    companion object {
        const val headerKey:String="header_"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var request=chain.request()
        var body=request.body()
        when(body){
            is JsonRequestBody -> {
                var newBuilder:Request.Builder=addHeader(request.newBuilder(),body.headers)
                body.setHeaders(null)
                request=newBuilder.post(body).build()
            }
            is FormBody ->{
                val headers: HashMap<String, String> = HashMap()
                var formBuilder = FormBody.Builder();
                var size=body.size()
                for(index in 0 until size){
                    var key=body.name(index)
                    var value=body.value(index)
                    if(key.startsWith(headerKey)){
                        //is header
                        var relHeaderName=getHeaderKey(key)
                        headers[relHeaderName]=value
                    }else{
                        formBuilder.addEncoded(key,value)
                    }
                }
                request=addHeader(request.newBuilder(),headers).post(formBuilder.build()).build()
            }
        }
        return chain.proceed(request);
    }
    fun getHeaderKey(name:String):String{
        return name.substring(headerKey.length)

    }
    fun addHeader(newBuilder:Request.Builder,headers: Map<String, String>?) :Request.Builder{
        headers?.forEach{
            newBuilder.addHeader(it.key,it.value)
        }
        return newBuilder
    }
}