package com.zz.libcore.network.converterfactory

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/6 11:15
 * 类描述：
 */
class CustomResponseBodyConverter<T>(gson: Gson,adapter:TypeAdapter<T>) : Converter<ResponseBody, T>{
    var gson:Gson?=null
    var adapter:TypeAdapter<T>? =null
    init {
        this.gson=gson
        this.adapter=adapter
    }

    override fun convert(value: ResponseBody): T? {
        TODO("Not yet implemented")
    }


}