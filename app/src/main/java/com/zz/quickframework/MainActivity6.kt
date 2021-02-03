package com.zz.quickframework

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zz.libcore.ui.ZBaseActivity
import com.zz.libcore.utils.AppUtil
import com.zz.myapplication1.R
import com.zz.myapplication1.databinding.ActivityMain6Binding
import com.zz.quickframework.vm.Abc

class MainActivity6 : ZBaseActivity() {
    private val viewBinding:ActivityMain6Binding by viewBinding(R.id.root);
    // Without reflection
//    private val viewBinding by viewBinding(ActivityMain6Binding::bind, R.id.root)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.tvTips.text="cccc"
//        Log.d("dd",AppUtil.getVersionCode(this).toString())
//        Log.d("dd",AppUtil.getVersionName(this).toString())
//        hi(age=12,name="ccc");
//        hi("tom")
//        test()
        a(6,"aak",::b)
        (::b)(1,"aa")
        Abc.get()
    }

    fun test(){
        var list= intArrayOf(1,2,3,4);
        var map= mapOf("a" to 1,"b" to 2);
        var mulist = mutableListOf(1,2,3)
        val newList :List<Int> =list.filter {
            i->i!=2
        }
        newList.forEach {
            Log.d("dd","i=$it")
        }
        map.forEach { (t, u) ->
            Log.d("dd","key=$t,val=$u")
        }
        var range=20..40
        for(i in 0 until 10){
            Log.d("dd","i=$i")
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main6;
    }

    fun init(){
        viewBinding.tvTips.setOnClickListener(listener)
        viewBinding.tvTips.setOnClickListener(){
            click1(1)
            click2(2)
        }
        viewBinding.tvTips.setOnClickListener(fun(v:View):Unit{

        })
        viewBinding.tvTips.setOnClickListener({v:View->

        })
        var intarr= intArrayOf(1,2)
        top1()

        var list= listOf(1,2,34);
        var map= mapOf("a" to 1,"b" to 2);
        var mulist= mutableListOf(1,2,3)

        list.forEach {
            Log.d("dd","i=$it")
        }
        for(i in list){

        }
        var str:String?="hello";
        var length:Int=str?.length?:-1
    }
    var listener= View.OnClickListener { v ->
        v?.id;
        v?.alpha=1f
    }

    fun hi(isStu:Boolean=true,name:String="aaa",age:Int){
        Log.d("dd", "HI$name,age=$age")
    }
    fun test2(age:Int){
        when(age){
            1->{}
            2,3->{}
            else->{}
        }
    }

    lateinit var bb:(Int, String)->String
    var l=::b
    fun a(age:Int,name:String,funParam: (Int,String)->String){
        bb=funParam;
        var result=b(age,name)
        Log.d("dd", "result=$result")
        l(1,"")
    }
    fun b(age:Int,name:String):String{
        return "hello_$name==$age"
    }

    var click1=fun(age:Int):String{
        return age.toString()
    }

    var click2={age:Int->String
        Thread(object: Runnable{
            override fun run() {
                TODO("Not yet implemented")
            }
        })
        Thread{

        }.start()

    }


}

fun top1(){}