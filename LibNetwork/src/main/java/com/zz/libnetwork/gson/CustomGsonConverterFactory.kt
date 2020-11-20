package com.zz.libnetwork.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.ConstructorConstructor
import com.google.gson.internal.Excluder
import com.google.gson.internal.bind.CollectionTypeAdapterFactory
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory
import com.google.gson.internal.bind.MapTypeAdapterFactory
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/16 15:29
 * 类描述：gson 数据类型转换(容错处理)
 */
class CustomGsonConverterFactory {
    companion object{
        var integerAdapter=IntegerTypeAdapter()
        var longAdapter=LongTypeAdapter()
        var floatAdapter=FloatTypeAdapter()
        var doubleAdapter=DoubleTypeAdapter()
        var stringAdapter=StringTypeAdapter()
        var booleanAdapter=BooleanTypeAdapter()

        // 集合
        var jsonAdapterFactory: JsonAdapterAnnotationTypeAdapterFactory =
            JsonAdapterAnnotationTypeAdapterFactory(
                ConstructorConstructor(emptyMap())
            )

        //注册反射对象的处理器
        var rta = ReflectiveTypeAdapterFactory(
            ConstructorConstructor(emptyMap()),
            FieldNamingPolicy.IDENTITY,
            Excluder.DEFAULT,
            jsonAdapterFactory
        )
        //注册集合的处理器
        var cta=CollectionTypeAdapterFactory(ConstructorConstructor(emptyMap()))
        var mta=MapTypeAdapterFactory(ConstructorConstructor(emptyMap()),false)

        //注入 type adapter
        var gson: Gson =GsonBuilder()
            .registerTypeAdapterFactory(rta)
            .registerTypeAdapterFactory(cta)
            .registerTypeAdapterFactory(mta)
            .registerTypeAdapter(Int::class.java,integerAdapter)
            .registerTypeAdapter(Long::class.java,longAdapter)
            .registerTypeAdapter(Float::class.java,floatAdapter)
            .registerTypeAdapter(Double::class.java,doubleAdapter)
            .registerTypeAdapter(String::class.java,stringAdapter)
            .registerTypeAdapter(Boolean::class.java,booleanAdapter)
            .create()

        fun create(): Converter.Factory{
            return GsonConverterFactory.create(gson)
        }
    }
}