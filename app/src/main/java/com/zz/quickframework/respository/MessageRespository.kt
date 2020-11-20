package com.zz.quickframework.respository

import android.util.Log
import com.zz.libnetwork.params.RequestParams
import com.zz.quickframework.ApiManager
import com.zz.quickframework.Message
import com.zz.quickframework.NetResult
import com.zz.quickframework.respository.BaseRepository
import kotlinx.coroutines.delay

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 10:38
 * 类描述：
 */
class MessageRespository : BaseRepository() {

    suspend fun getMsgDatas( d:Long): NetResult<List<Message>>? {
        return sendRequest {
            Log.d("dd", "getData->${Thread.currentThread().name}")
            delay(d)
            var params = RequestParams()
            params.addParam("app_type", 1)
            params.addParam("version", "5.0.0")
            params.addParam("website", "rosegal")
            params.addParam("lang", "en")
            params.addParam("device_id", "1bba7b8629a0546dce091719995658eb0b7e")
            params.addParam("user_id", "11663")
            ApiManager.msgApi().getMenu1(params.createRequestBody()).await()
        }
    }

    suspend fun getMsgDatas2( d:Long): NetResult<List<Message>>? {
        return sendRequest {
            Log.d("dd", "getData->${Thread.currentThread().name}")
            delay(d)
            var params = RequestParams()
            params.addParam("app_type", 1)
            params.addParam("version", "5.0.0")
            params.addParam("website", "rosegal")
            params.addParam("lang", "en")
            params.addParam("device_id", "1bba7b8629a0546dce091719995658eb0b7e")
            params.addParam("user_id", "11663")
            ApiManager.msgApi().getMenu2(params.createRequestBody()).await()
        }
    }
}