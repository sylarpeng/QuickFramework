package com.zz.libcore.network.params

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 10:57
 * 类描述：
 */
class JsonRequestBody(body:RequestBody) : RequestBody() {
    var headers: Map<String, String>? = null
    private var requestBody:RequestBody?=null
    init {
        requestBody=body;
    }

    override fun contentType(): MediaType? {
        return requestBody?.contentType();
    }

    override fun writeTo(sink: BufferedSink) {
        requestBody?.writeTo(sink)
    }

    fun setHeaders(headers:Map<String, String>?):RequestBody{
        this.headers=headers;
        return this;
    }
    companion object{
        fun create(mediaType: MediaType,content:String):JsonRequestBody{
            return JsonRequestBody(RequestBody.create(mediaType,content))
        }

    }



}