package com.zz.quickframework

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.zz.libnetwork.gson.CustomGsonConverterFactory
import com.zz.libnetwork.params.RequestParams
import com.zz.myapplication1.R
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var tvTips:TextView?=null
    private var viewModel:TestViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTips=findViewById<TextView>(R.id.tv_tips)
        findViewById<TextView>(R.id.tv_btn).setOnClickListener(View.OnClickListener {
            tvTips?.text=null
//            test11()
//            testGson();
            startActivity(Intent(this,MainActivity2::class.java));
        })
        findViewById<TextView>(R.id.tv_cancel).setOnClickListener(View.OnClickListener {
            test3()
        })
        viewModel=ViewModelProviders.of(this).get(TestViewModel::class.java);

        viewModel!!.result.observe(this, androidx.lifecycle.Observer {
            printMsg(it.msg!!)
        })
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



    private fun testGson(){
        try {
            var jsonArray=JSONArray();
            var goods=JSONObject();
            goods.put("id","1")
            goods.put("name","test1")
            var goods2=JSONObject();
            goods2.put("id","")
            goods2.put("name","test222")
            jsonArray.put(goods)
            jsonArray.put(goods2)

            var json=JSONObject();
            json.put("gender","")
            json.put("user_name","test")
            json.put("sex","")
            json.put("sex1","")
            json.put("sex2","")
            json.put("sex3",jsonArray)
            json.put("goods",jsonArray)
            json.put("vip","1")
//            json.put("good","")
            var stu=CustomGsonConverterFactory.gson.fromJson(json.toString(),Student::class.java)
            printMsg(stu.toString())
        }catch (e :java.lang.Exception){
            e.printStackTrace();
        }

    }

    private fun test1(){
        runBlocking {
            repeat(8){
                printMsg("协程执行$it 线程id：${Thread.currentThread().name}");
                delay(1000);
                printMsg("协程执行完成$it 线程id：${Thread.currentThread().name}");
            }
        }
    }
    private var job : Job?=null
    private fun test2(){
        printMsg("执行主线程，id=${mainLooper.thread.id}")
        job=GlobalScope.launch {
            delay(4000)
            printMsg("执行协程结束")
        }
        printMsg("执行主线程结束")
    }
    private fun test3(){
        job?.cancel()
    }
    private fun test4(){
        //顺序
        GlobalScope.launch {
            printMsg("协程执行线程--：${Thread.currentThread().name}");
            printMsg("开始请求token...")
            var token:String=getToken();
            printMsg("获取token成功，token=${token}")
            printMsg("开始请求userinfo...")
            var user:String=getUser(token)
            printMsg("获取user成功，user=${user}")
            withContext(Dispatchers.Main){
                printMsg("协程执行线程--：${Thread.currentThread().name}");
            }

        }
    }

    private fun test5(){
        GlobalScope.launch {
            var result1=async {
                getResult1();
            }
            var result2=async {
                getResult2()
            }
            var result=result1.await()+result2.await();
            printMsg("sum=${result}");

        }

    }

    private fun test6(){
        var url="https://jsonplaceholder.typicode.com/";
        var retrofit=Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val testApi=retrofit.create(TestApi::class.java)
        testApi.getDatas(1).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                printMsg("success")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                printMsg("fail")
            }

        })
    }
    private fun test7(){
        var url="https://jsonplaceholder.typicode.com/";
        var retrofit=Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val testApi=retrofit.create(TestApi::class.java)
        testApi.getDatas2().enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                printMsg("success")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                printMsg("fail")
            }

        })
    }
    private fun test9(){
        ApiManager.testApi().getDatas(2).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                printMsg("success")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                printMsg("fail")
            }

        })
    }

    private fun test10(){
        var params:RequestParams= BaseRequest()
        params.isJsonParams=true
        params.addParam("userId","1234")
        ApiManager.testApi().getDatas3(params.createRequestBody()).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                printMsg("success")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                printMsg("fail")
            }

        })
    }

    private fun test11(){
        viewModel!!.requestData()

        var params= RequestParams()
        params.addParam("app_type",1)
        params.addParam("version","5.0.0")
        params.addParam("website","rosegal")
        params.addParam("lang","en")
        params.addParam("device_id","1bba7b8629a0546dce091719995658eb0b7e")
        params.addParam("user_id","11663")
        ApiManager.msgApi().getMenu(params.createRequestBody()).enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                var msg=response.body()
                printMsg("success")
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                t.printStackTrace()
                printMsg("fail")
            }

        })
    }

    private fun test12(){
        try {
            var gson: Gson = Gson()
//            var obj:JSONObject= JSONObject();
//            obj.put("userId","123")
//            obj.put("title","hahaword")
//            var u:User?=gson.fromJson(obj.toString(),User::class.java)
//            printMsg(u?.title!!)
//
//            var json="{\"code\":200,\"message\":\"succeed\",\"data\":{\"model\":\"dlt\",\"layoutType\":\"heng\",\"layoutId\":\"1\",\"centers\":[{\"id\":\"1\",\"width\":\"50\",\"height\":\"50\",\"left\":\"0\",\"top\":\"0\",\"type\":\"image\",\"resource\":[\"http://imgs.yunbiao.tv/imgserver/resource/2020/09/21/dd1aadec-0d22-4b17-bb74-759a501f56c2.jpg\",\"http://imgs.yunbiao.tv/imgserver/resource/2020/09/21/92d47b96-ba92-4028-a4c0-b8c0d6d94f19.jpg\"],\"subtitle\":{\"sid\":null,\"scolor\":null,\"sbgcolor\":null,\"scontent\":null,\"ssize\":null,\"slucency\":null,\"sspeed\":null,\"lid\":null}},{\"id\":\"2\",\"width\":\"50\",\"height\":\"50\",\"left\":\"50\",\"top\":\"0\",\"type\":\"web\",\"resource\":[\"https://www.sporttery.cn/kj/kjlb.html?dlt\"],\"subtitle\":{\"sid\":null,\"scolor\":null,\"sbgcolor\":null,\"scontent\":null,\"ssize\":null,\"slucency\":null,\"sspeed\":null,\"lid\":null}},{\"id\":\"3\",\"width\":\"100\",\"height\":\"50\",\"left\":\"0\",\"top\":\"50\",\"type\":\"video\",\"resource\":[\"http://imgs.yunbiao.tv/imgserver/resource/2020/09/21/f97fd3de-7094-4c0e-bad4-f4c039ccb2aa.mp4\"],\"subtitle\":{\"sid\":null,\"scolor\":null,\"sbgcolor\":null,\"scontent\":null,\"ssize\":null,\"slucency\":null,\"sspeed\":null,\"lid\":null}}]}}";
//
//            var bb: BaseBean?=gson.fromJson(json, BaseBean::class.java)
//            printMsg(bb?.message!!)

            var json="{\"id\": \"xxx\",\"macs\": [\"1001\",\"1002\",\"1003\"]}";
            var jsonObj=JSONObject(json)
            val jsonArray = jsonObj.getJSONArray("macs")
//            val list: List<String> = JSONArrays(jsonArray, String::class.java) // 过时方法

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun test13(){
        var now=Date(1604565448000);
        var format=SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.US);
        var format2=SimpleDateFormat("MMM.dd,yyyy 'at' HH:mm:ss", Locale.US);
        Log.d("dd","time1="+format.format(now))
        Log.d("dd","time2="+format2.format(now))
    }



    /**
     * 通信token，由 data 和 接口秘钥 连接在一起，然后进行md5编码。
     * @return
     */
    private fun getEnFmToken(content: String): String {
        return MD5Util.md5(content + "lJoxSwx8MFxE3dKK")
    }


    private suspend fun getToken():String{
        delay(2000);
        return "abc123";
    }

    private suspend fun getUser(token:String):String{
        delay(2000);
        return "user-${token}"
    }

    private suspend fun getResult1():Int{
        printMsg("开始请求Result1...")
        delay(2000);
        printMsg("开始请求Result1 完成...")
        return 1;
    }
    private suspend fun getResult2():Int{
        printMsg("开始请求Result2...")
        delay(4000);
        printMsg("开始请求Result2 完成...")
        return 2;
    }


    private fun printMsg(msg:String){
        val msg1:String = tvTips?.text.toString()+"\n"+msg
        Log.d("dd",msg1);
        tvTips?.text=msg1
    }
}