package com.zz.quickframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zz.libcore.ui.ZBaseActivity
import com.zz.myapplication1.R
import com.zz.myapplication1.databinding.ActivityMain6Binding

class MainActivity6 : ZBaseActivity() {
    private val viewBinding:ActivityMain6Binding by viewBinding(R.id.root);
    // Without reflection
//    private val viewBinding by viewBinding(ActivityMain6Binding::bind, R.id.root)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.tvTips.text="cccc"
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main6;
    }
}