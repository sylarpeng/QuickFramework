package com.zz.quickframework

import okhttp3.*
import java.lang.Exception

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/5 9:18
 * 类描述：加/解密拦截器
 */
class EncryptionInterceptor:Interceptor  {
    val token="token"
    val encryKey="lJoxSwx8MFxE3dKK"
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if(isMessageHost(request.url().host())){
            var newBuilder=addEncryptionHeader(request,request.headers())
            request = newBuilder.post(request.body()).build()
        }
        return chain.proceed(request)

    }

    private fun addEncryptionHeader(request: Request, headers: Headers):Request.Builder {
        val newBuilder = request.newBuilder()
        val body = request.body()
        if(body is RequestBody){
            var bodyStr=body2String(body)
            newBuilder.addHeader("token",getEnToken(bodyStr,encryKey))
        }
        val size = headers.size()
        if(size>0){
            for(index in 0 until  size){
                newBuilder.addHeader(headers.name(0),headers.value(0))
            }
        }
        return newBuilder

    }

    private fun body2String(body:RequestBody):String{
        var buffer=okio.Buffer()
        try {
            body.writeTo(buffer)
            return buffer.readUtf8()
        }catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }

    /**
     * 通信token，由 data 和 接口秘钥 连接在一起，然后进行md5编码。
     * @return
     */
    private fun getEnToken(content: String,key:String): String {
        return MD5Util.md5(content + key)
    }

    private fun isMessageHost(host:String): Boolean {
        return Constants.HOST_MESSAGE.contains(host,true)
    }
}