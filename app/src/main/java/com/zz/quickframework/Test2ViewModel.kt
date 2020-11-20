package com.zz.quickframework

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.zz.quickframework.vm.BaseViewModel
import com.zz.quickframework.respository.MessageRespository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/17 17:10
 * 类描述：
 */
class Test2ViewModel : BaseViewModel() {
    var messageResult = MutableLiveData<NetResult<List<Message>>>()
    var messageRespository =
        MessageRespository()

    fun requestData(context:FragmentActivity){
        launchUI(context) {
//            var result= messageRespository.getMsgDatas(2000)
//            var result2= messageRespository.getMsgDatas2(2000)
//            messageResult.postValue(result)
            var token=async {
                getToken();
            }
            var user=async {
                getUser();
            }
            Log.d("dd","after...")
            var ss=token.await()+user.await()
            Log.d("dd","finished...")
            messageResult.postValue(null)
        }
    }

    suspend fun getToken():String?{
        Log.d("dd","token---start")
        delay(2000)
        Log.d("dd","token---end")
        return "token111"
    }
    suspend fun getUser():String?{
        Log.d("dd","user---start")
        delay(4000)
        Log.d("dd","user---end")
        return "user222"
    }

}