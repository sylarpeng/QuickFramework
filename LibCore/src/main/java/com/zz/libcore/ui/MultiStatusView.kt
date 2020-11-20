package com.zz.libcore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 18:07
 * 类描述：多状态view 容器管理类(数据加载页，网络异常页,空数据页，结果页)
 */
class MultiStatusView : FrameLayout{
    companion object{
        const val VIEW_TYPE_CONTENT=0
        const val VIEW_TYPE_LOADING=1
        const val VIEW_TYPE_EMPTY=2
        const val VIEW_TYPE_ERROR=3
    }

    @IntDef(
        VIEW_TYPE_CONTENT,
        VIEW_TYPE_LOADING,
        VIEW_TYPE_EMPTY,
        VIEW_TYPE_ERROR
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ViewType{}

    var viewHelper: IStatusView?=null
    var contentView:View?=null
    var loadingView:View?=null
    var emptyView:View?=null
    var errorView:View?=null

    constructor(context: Context):super(context){
        init(context)
    }
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        init(context)
    }

    private fun init(context: Context) {

    }

    fun addView(@ViewType viewType:Int, view: View?){
        if(view==null){
            return
        }
        when (viewType) {
            VIEW_TYPE_CONTENT -> contentView=view
            VIEW_TYPE_LOADING -> loadingView=view
            VIEW_TYPE_EMPTY -> emptyView=view
            VIEW_TYPE_ERROR ->  errorView=view
            else -> {
            }
        }
        this.addView(view)
        refreshView(viewType)
    }


    fun showContentView(){
        refreshView(VIEW_TYPE_CONTENT)
    }
    fun showLoadingView(){
        if(loadingView==null){
            addView(VIEW_TYPE_LOADING,viewHelper?.getLoadingView())
        }else{
            refreshView(VIEW_TYPE_LOADING)
        }
    }
    fun showEmptyView(){
        if(emptyView==null){
            addView(VIEW_TYPE_EMPTY,viewHelper?.getEmptyView())
        }else{
            refreshView(VIEW_TYPE_EMPTY)
        }
    }

    fun showErrorView(){
        if(errorView==null){
            addView(VIEW_TYPE_ERROR,viewHelper?.getErrorView())
        }else{
            refreshView(VIEW_TYPE_ERROR)
        }
    }


    private fun refreshView(@ViewType viewType:Int) {
        contentView?.visibility=if(viewType== VIEW_TYPE_CONTENT) VISIBLE else GONE
        loadingView?.visibility=if(viewType== VIEW_TYPE_LOADING) VISIBLE else GONE
        emptyView?.visibility=if(viewType== VIEW_TYPE_EMPTY) VISIBLE else GONE
        errorView?.visibility=if(viewType== VIEW_TYPE_ERROR) VISIBLE else GONE
    }



}