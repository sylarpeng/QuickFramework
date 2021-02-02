package com.zz.quickframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zz.libcore.ui.ZToolbarActivity
import com.zz.myapplication1.R

class MainActivity5 : ZToolbarActivity() {
    override fun getFragment(): Fragment? {
        return Fragment5();
    }

//
//    override fun getLayoutResId(): Int {
//        return R.layout.activity5;
//    }
}