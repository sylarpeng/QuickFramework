package com.zz.quickframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zz.libcore.ui.ZBaseActivity
import com.zz.myapplication1.R

class MainActivity4 : ZBaseActivity() {


    override fun getFragment(): Fragment? {
        return Fragment4()
    }


}