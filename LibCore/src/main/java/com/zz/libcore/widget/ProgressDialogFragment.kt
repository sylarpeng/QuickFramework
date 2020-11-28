package com.zz.libcore.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.zz.libcore.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/18 15:28
 * 类描述：网络请求时加载弹窗
 */
open class ProgressDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLoadingLayoutResId(), container, false);
    }

    open fun getLoadingLayoutResId():Int{
        return R.layout.fragment_loading_dialog
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val dialog = dialog
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }

}