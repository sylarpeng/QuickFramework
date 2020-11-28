package com.zz.quickframework

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zz.libcore.widget.pullrefresh.PullRefreshView
import com.zz.myapplication1.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 18:44
 * 类描述：
 */
class Fragment3 : RgRefreshFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_base_refresh_list;
    }

    var refreshView: PullRefreshView?=null
    var recyclerView: RecyclerView?=null

    override fun getPullRefreshView(): PullRefreshView? {
        return refreshView
    }

    override fun initView(view: View) {
        refreshView=view.findViewById(R.id.refresh_view)
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
        return LinearLayoutManager(mContext)
    }

    override fun onRefresh() {
        recyclerView!!.postDelayed({ stopRefresh() }, 2000)
    }

    inner class MyAdapter : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val textView = TextView(mContext)
            textView.setPadding(0,20,0,20)
            return MyHolder(textView)
        }

        override fun getItemCount(): Int {
            return 150;
        }

        override fun onBindViewHolder(holder:MyHolder, position: Int) {
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
