package com.zz.quickframework

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.zz.myapplication1.R

class MainActivity2 : AppCompatActivity() {
    var tvTips:TextView?=null;
    var tvClick:TextView?=null;
    private var viewModel:Test2ViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()
    }
    override fun attachBaseContext(newBase: Context?) {
        val newContext = LanuageUtil.changeAppLanguage(newBase)
        super.attachBaseContext(newContext)
        try {
            val resources = newContext.resources
            val configuration = resources.configuration;
            if(Build.VERSION.SDK_INT>=24){
                applyOverrideConfiguration(configuration)
                return
            }
            applyOverrideConfiguration(configuration)
            resources.updateConfiguration(configuration,resources.displayMetrics)


        }catch (e:Exception){}
    }

    private fun init() {
        tvTips=findViewById<TextView>(R.id.tips)
        tvClick=findViewById<TextView>(R.id.click)
        tvClick?.setOnClickListener(View.OnClickListener {
            printMsg("start request")
            viewModel?.requestData(this)
        })
        viewModel= ViewModelProviders.of(this).get(Test2ViewModel::class.java);
        viewModel!!.messageResult.observe(this, androidx.lifecycle.Observer {
            if(it!=null){
                printMsg(it.msg!!)
            }else{
                printMsg("fail")
            }

        })

        findViewById<TextView>(R.id.change).setOnClickListener(View.OnClickListener {
            LanuageUtil.changeAppLanguage(this)
            tvClick?.setText(R.string.click)
        })


        findViewById<TextView>(R.id.main3).setOnClickListener(View.OnClickListener {

            startActivity(Intent(this,MainActivity3::class.java))
        })
        findViewById<TextView>(R.id.main4).setOnClickListener(View.OnClickListener {

            startActivity(Intent(this,MainActivity4::class.java))
        })

        findViewById<TextView>(R.id.main5).setOnClickListener(View.OnClickListener {

            startActivity(Intent(this,MainActivity5::class.java))
        })
    }


    private fun printMsg(msg:String){
        val msg1:String = tvTips?.text.toString()+"\n"+msg
        tvTips?.text=msg1
    }
}