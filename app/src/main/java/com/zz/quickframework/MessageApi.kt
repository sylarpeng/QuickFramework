package com.zz.quickframework


import kotlinx.coroutines.Deferred
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
interface MessageApi {
    @POST("api/letter/menu")
    fun getMenu(@Body requestBody: RequestBody) : Call<Message>

    @POST("api/letter/menu")
    suspend fun getMenu1(@Body requestBody: RequestBody) : NetResult<List<Message>>

    @POST("api/letter/menu2")
    fun getMenu2(@Body requestBody: RequestBody) : Deferred<NetResult<List<Message>>>
}