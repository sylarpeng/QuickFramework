package com.zz.quickframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zz.libcore.ui.ZToolbarActivity

class MainActivity5 : ZToolbarActivity() {
    override fun getFragment(): Fragment? {
        return Fragment5();
    }
}