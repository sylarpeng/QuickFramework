package com.zz.quickframework

import com.zz.libnetwork.params.RequestParams

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/23 14:49
 * 类描述：
 */
class BaseRequest : RequestParams() {
    init {
        addPublicHeaders()
        addPublicParams()
    }

    private fun addPublicHeaders(){
        addHeader("token","token111")
        addHeader("stage","true")
    }
    private fun addPublicParams(){
        addParam("platform","1")
        addParam("deviceId","xxx")
    }
}