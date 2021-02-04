package com.zz.quickframework

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zz.libcore.network.params.RequestParams
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/17 17:10
 * 类描述：
 */
class TestViewModel : ViewModel() {
    var result = MutableLiveData<NetResult<List<Message>>>()

    fun requestData(){
        var params= RequestParams()
        params.addParam("app_type",1)
        params.addParam("version","5.0.0")
        params.addParam("website","rosegal")
        params.addParam("lang","en")
        params.addParam("device_id","1bba7b8629a0546dce091719995658eb0b7e")
        params.addParam("user_id","11663")

        viewModelScope.launch {
            try {
                delay(3000)
                val data= withContext(Dispatchers.IO){
                    ApiManager.msgApi().getMenu2(params.createRequestBody())
                }
                result.postValue(data.await())
            }catch (e:Exception){
                e.printStackTrace()
                result.postValue(null)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        Log.d("dd","request cancnel");
    }
}