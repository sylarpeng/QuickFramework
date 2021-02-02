package com.zz.libcore.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zz.libcore.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/19 14:21
 * 类描述：fragment 基类
 */
open class ZBaseFragment(layResId:Int) : Fragment(){
    var mContext:FragmentActivity?=null
    private var multiStatusView:MultiStatusView?=null
    protected open var bindingView:View?=null
    open var contentLayoutResId:Int=0
    init {
        this.contentLayoutResId=layResId;
    }
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
        bindingView?.let {
            bindView(it)
        }
        initView(view)
    }

    protected open fun initData() {

    }

    /**
     * viewbinding
     */
    protected open fun bindView(contentView:View){}

    protected open fun initView(view:View) {

    }

    /**
     * 初始化多状态view
     * 默认只存放contentView
     */
    private fun initMultiStatusView(view: View) {
        if(view is MultiStatusView){
            multiStatusView=view
            multiStatusView?.run{
                removeAllViews()
                viewHelper=getStatusView()
                var contentView=getLayoutView()
                if(contentView!=null){
//                    bindingView=contentView.getBindView();
                    addView(MultiStatusView.VIEW_TYPE_CONTENT,contentView)
                }else{
                    bindingView=View.inflate(mContext,contentLayoutResId,null);
                    addView(MultiStatusView.VIEW_TYPE_CONTENT,bindingView)
                }
            }

        }
    }

    open fun getStatusView(): IStatusView?{
       return ZStatusView(mContext)
    }

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