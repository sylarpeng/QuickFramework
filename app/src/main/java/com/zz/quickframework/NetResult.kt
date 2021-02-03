package com.zz.quickframework

import com.zz.quickframework.beans.BaseBean
import java.lang.Exception

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/17 17:17
 * 类描述：
 */
 class NetResult<T> constructor() : BaseBean() {
    var statusCode:Int=0
    var msg:String=""
    var data:T?=null
}