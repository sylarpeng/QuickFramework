package com.zz.quickframework

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zz.myapplication1.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 18:44
 * 类描述：
 */
class Fragment3 : RgRefreshFragment(R.layout.fragment_base_refresh_list) {

    private var linearLayoutManager:LinearLayoutManager?=null;

    var recyclerView: RecyclerView?=null

    override fun initView(view: View) {
        recyclerView=view.findViewById(R.id.rv)
        initRv()
    }

    private fun initRv() {
        recyclerView?.layoutManager=getLayoutManager()
        recyclerView?.adapter=getRVAdapter()
    }


    fun getRVAdapter(): MyAdapter? {
        return MyAdapter();
    }

    fun getLayoutManager(): RecyclerView.LayoutManager {
        linearLayoutManager=GridLayoutManager(mContext,2)
        return linearLayoutManager!!
    }

    override fun onRefresh() {
        Log.d("dd", "reuqest_start")
        recyclerView!!.postDelayed({
            Log.d("dd", "reuqest_end")
            stopRefresh()
        }, 3000)
    }

    inner class MyAdapter : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val textView = TextView(mContext)
            textView.setPadding(0, 20, 0, 20)
            return MyHolder(textView)
        }

        override fun getItemCount(): Int {
            return 150;
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.tx?.text="text_${position}"
        }
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tx: TextView?=null
        init {
            tx=itemView as TextView
        }
    }
}
