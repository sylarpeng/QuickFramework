package com.zz.libcore.widget

import androidx.fragment.app.FragmentActivity

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 15:17
 * 类描述：
 */
class ProgressDialog {
    companion object{
        var dialogFragment:ProgressDialogFragment?=null

        fun show(context:FragmentActivity){
            show(context,false)
        }
        fun show(context:FragmentActivity,isCancelable:Boolean){
            if(context.isDestroyed){
                return
            }
            if(dialogFragment==null){
                dialogFragment= ProgressDialogFragment()
            }
            if(dialogFragment?.dialog!=null && dialogFragment!!.dialog!!.isShowing){
                return
            }
            dialogFragment?.isCancelable=isCancelable
            dialogFragment?.show(context.supportFragmentManager,"show")
        }

        fun dissmiss(){
            dialogFragment?.dismiss()
        }

    }
}