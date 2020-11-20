package com.zz.libnetwork.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.Exception
import java.lang.reflect.Type

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/16 11:05
 * 类描述：
 */
class StringTypeAdapter : JsonDeserializer<String> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
        if(json==null){
            return ""
        }
        return try {
            when {
                json!!.isJsonArray -> {
                    json.asJsonArray.toString()
                }
                json!!.isJsonObject -> {
                    json.asJsonObject.toString()
                }
                else -> {
                    json!!.asString
                }
            }

        } catch (e:Exception) {
            ""
        }
    }
}