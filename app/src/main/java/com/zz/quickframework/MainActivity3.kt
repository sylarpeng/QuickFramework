package com.zz.quickframework


import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zz.libcore.ui.ZToolbarActivity
import com.zz.myapplication1.R

class MainActivity3 : ZToolbarActivity() {

    override fun getFragment(): Fragment? {
        return Fragment3()
    }

    override fun getToolbarTitle(): String {
        return "main3 title aaabbbcccggggfffsss"
    }


    override fun getMenuResId(): Int {
        return R.menu.main_menu
    }

    override fun initView() {
        updateMenuIconBadge(R.id.cart,R.mipmap.ic_cart, View.OnClickListener {
            Toast.makeText(this,"cat..",Toast.LENGTH_LONG).show()
        })
        updateMenuIconBadge(R.id.cart,3)
        updateMenuIconVisible(R.id.cat,true)
        updateMenuIconVisible(R.id.more,true)
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
             R.id.cat ->{Toast.makeText(this,"cat..",Toast.LENGTH_LONG).show()}
            R.id.cart->{Toast.makeText(this,"cart..",Toast.LENGTH_LONG).show()}
            R.id.more->{Toast.makeText(this,"more..",Toast.LENGTH_LONG).show()}
            else ->{}
        }
        return true
    }



}