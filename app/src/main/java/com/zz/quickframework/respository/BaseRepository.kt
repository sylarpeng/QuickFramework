package com.zz.quickframework.respository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 10:34
 * 类描述：网络访问基类,在IO线程中进行网络请求
 */
open class BaseRepository {
    suspend fun <Any> sendRequest(call:suspend() ->Any): Any?{
        return withContext(Dispatchers.IO){
            try {
                call.invoke()
            }catch (e:Exception){
                e.printStackTrace()
                null
            }

        }
    }
}