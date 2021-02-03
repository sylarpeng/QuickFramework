package com.zz.quickframework

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/10/22 11:49
 * 类描述：
 */
class User{
    val userId:String?=null
    var title:String?=null
    get() {
        return field+"111"
    }
    set(value) {
        field="aaa"+value
    }

}