package com.zz.libnetwork.converterfactory

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/6 11:13
 * 类描述：自定义gson解析，处理字段类型不一致异常
 */
class CustomGsonConverterFactory(gson: Gson) : Converter.Factory() {
    var gson: Gson?=null
    init {
        this.gson=gson
    }

    /**
     * Create an instance using a default [Gson] instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    companion object{
        fun create(): CustomGsonConverterFactory? {
            return create(Gson())
        }

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and decoding from JSON
         * (when no charset is specified by a header) will use UTF-8.
         */
        // Guarding public API nullability.
        fun create(gson: Gson?): CustomGsonConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return CustomGsonConverterFactory(gson)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }


    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter: TypeAdapter<*> =gson!!.getAdapter(TypeToken.get(type))

        return CustomResponseBodyConverter(gson!!, adapter)
    }
}