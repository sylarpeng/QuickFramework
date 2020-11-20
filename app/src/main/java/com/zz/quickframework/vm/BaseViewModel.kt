package com.zz.quickframework.vm

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zz.libcore.widget.ProgressDialog
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 9:33
 * 类描述：ViewModel中引入协程，如果直接使用CoroutineScope，那么需要在onCleared()方法中取消协程，
 * 如果忘记取消协程那么会导致出现内存泄漏等问题，此时需要使用ViewModel扩展属性viewModelScope来实现协程作用域
 */
open class BaseViewModel : ViewModel() {

    fun launchUI(block:suspend CoroutineScope.() -> Unit){
        launchUI(null,block)
    }
    /**
     * 运行在主线程的协程
     * context 不为空时 ，展示加载进度圈
     */
    fun launchUI(context:FragmentActivity?,block:suspend CoroutineScope.() -> Unit){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                if(context!=null){
                    ProgressDialog.show(context)
                }
                block()
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                if(isActive){
                    ProgressDialog.dissmiss()
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}