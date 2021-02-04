package com.zz.quickframework

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.permissionx.guolindev.PermissionX
import com.zz.libcore.ui.ZBaseActivity
import com.zz.libcore.utils.DensityUtil
import com.zz.myapplication1.R
import com.zz.myapplication1.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    var tvTips:TextView?=null;
    var tvClick:TextView?=null;
    var tvMain5:TextView?=null;
    private var viewModel:Test2ViewModel?=null
    private lateinit var binding: ActivityMain2Binding
    var flag:Boolean=false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tips.text="bbb"
        init()
        initLocation();

        binding.badge.show("46")
        binding.badge1.show()
        binding.badge2.setOnClickListener{
            if(flag){
                binding.badge2.show("67")
            }else{
                binding.badge2.hide()
            }
            flag=!flag
        }
    }


    private var xLocation=0
    private var yLocation=0
    private fun initLocation() {
        val location = IntArray(2)
        tvMain5?.post(Runnable {
            tvMain5?.getLocationOnScreen(location)
            xLocation = location[0];
            yLocation = location[1];
            Log.d("dd","x=${xLocation},y=${yLocation}")
            setLocation()
        })
    }

    private fun setLocation(){
        var root=findViewById<ConstraintLayout>(R.id.csl_root);
        var set=ConstraintSet()
        set.clone(root)
        set.clear(R.id.tv_main5,ConstraintSet.TOP)
        set.connect(R.id.tv_main5,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,yLocation-DensityUtil.getStatusbarHeight(this))
        set.connect(R.id.tv_main5,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,xLocation)
        set.applyTo(root)


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
            resources.updateConfiguration(configuration, resources.displayMetrics)


        }catch (e: Exception){}
    }

    private fun init() {
        tvTips=findViewById<TextView>(R.id.tips)
        tvClick=findViewById<TextView>(R.id.click)
        tvMain5=findViewById<TextView>(R.id.main5)
        tvClick?.setOnClickListener(View.OnClickListener {
            printMsg("start request")
            viewModel?.requestData(this)
        })
        viewModel= ViewModelProviders.of(this).get(Test2ViewModel::class.java);
        viewModel!!.messageResult.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                printMsg(it.msg!!)
            } else {
                printMsg("fail")
            }

        })

        findViewById<TextView>(R.id.change).setOnClickListener(View.OnClickListener {
            LanuageUtil.changeAppLanguage(this)
            tvClick?.setText(R.string.click)
        })


        findViewById<TextView>(R.id.main3).setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, MainActivity3::class.java))
        })
        findViewById<TextView>(R.id.main4).setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, MainActivity4::class.java))
        })

        findViewById<TextView>(R.id.main5).setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, MainActivity5::class.java))
        })
        findViewById<TextView>(R.id.permisson).setOnClickListener(View.OnClickListener {
            PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason { scope, deniedList ->
                    val message = "PermissionX needs following permissions to continue"
                    scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny")
                }
                .onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(
                        deniedList,
                        "您需要去应用程序设置当中手动开启权限",
                        "我已明白",
                        "取消"
                    )
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        Toast.makeText(this, " all success ", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, " deniedList=${deniedList}", Toast.LENGTH_LONG).show()
                    }
                }
        })
    }


    private fun printMsg(msg: String){
        val msg1:String = tvTips?.text.toString()+"\n"+msg
        tvTips?.text=msg1
    }
}