package com.zz.quickframework


import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/22 11:43
 * 类描述：
 */
interface TestApi {

    @GET("todos/{id}")
    fun getDatas(@Path("id") id:Int) : Call<User>

    @POST("posts")
    fun getDatas2() : Call<Any>

    @POST("posts/1")
    fun getDatas3(@Body requestBody: RequestBody) : Call<User>
}