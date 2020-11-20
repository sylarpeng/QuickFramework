package com.zz.quickframework

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/16 11:09
 * 类描述：
 */
public class Student {
    var gender:Int=0
    var sex:Float=0f
    var user_name:String?=null

    var sex1:Double=0.0
    var sex2:Long=0
    var sex3:String?=null

   var goods:List<Goods>?=null
   var good:Goods?=null
    var vip:Boolean=false

    companion object class Goods{
        var id:Int=0
        var name:String?=null
    }
}