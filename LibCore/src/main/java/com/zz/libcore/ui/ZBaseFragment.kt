package com.zz.libcore.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zz.libcore.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 14:21
 * 类描述：fragment 基类
 */
open class ZBaseFragment : Fragment(){
    var mContext:FragmentActivity?=null
    private var multiStatusView:MultiStatusView?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_base,container,false)
        initMultiStatusView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView(view)
    }

    protected open fun initData() {

    }
    protected open fun initView(view:View) {

    }

    /**
     * 初始化多状态view
     * 默认只存放contentView
     */
    private fun initMultiStatusView(view: View) {
        if(view!=null && view is MultiStatusView){
            multiStatusView=view
            multiStatusView?.run{
                removeAllViews()
                viewHelper=getStatusView()
                var contentView=getLayoutView()
                if(contentView!=null){
                    addView(MultiStatusView.VIEW_TYPE_CONTENT,contentView)
                }else{
                    addView(MultiStatusView.VIEW_TYPE_CONTENT,View.inflate(mContext,getLayoutResId(),null))
                }

            }

        }
    }

    open fun getStatusView(): IStatusView?{
       return ZStatusView(mContext)
    }

    @LayoutRes
    protected open fun getLayoutResId():Int=0

    protected open fun getLayoutView():View?=null

    protected fun showContentView(){
        multiStatusView?.showContentView()
    }
    protected fun showLoadingView(){
        multiStatusView?.showLoadingView()
    }
    protected fun showEmptyView(){
        multiStatusView?.showEmptyView()
    }
    protected fun showErrorView(){
        multiStatusView?.showErrorView()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=activity
    }

    override fun onDetach() {
        super.onDetach()
        mContext=null
    }
}