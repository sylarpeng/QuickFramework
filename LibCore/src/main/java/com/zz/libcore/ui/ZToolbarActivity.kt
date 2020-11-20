package com.zz.libcore.ui

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View.OnClickListener
import androidx.annotation.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.zz.libcore.R
import com.zz.libcore.widget.BadgeActionProvider

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/20 9:21
 * 类描述：帶toolbar activity
 * 支持设置菜单角标
 */
open class ZToolbarActivity : ZBaseActivity(), Toolbar.OnMenuItemClickListener {
    var toolbar:Toolbar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fragment=getFragment()
        if(fragment!=null){
            supportFragmentManager.beginTransaction().replace(R.id.fl_root_container,fragment!!,"").commit()
        }
        initToolbar()
        initView()
    }

    open fun initView() {}

    private fun initToolbar() {
        toolbar=findViewById<Toolbar>(R.id.toolbar)
        val layoutParams = toolbar?.layoutParams
        if(layoutParams is AppBarLayout.LayoutParams){
            if(canToolbarScroll()){
                layoutParams.scrollFlags= SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
            }else{
                layoutParams.scrollFlags= SCROLL_FLAG_NO_SCROLL
            }
        }
        //title
        toolbar?.title=getToolbarTitle()

        //icon
        toolbar?.setNavigationIcon(getNavigationIcon())
        toolbar?.setNavigationOnClickListener(OnClickListener {
            onFinish()
        })

        //去除阴影
        if(!showToolbarShadow()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(toolbar?.parent is AppBarLayout){
                    var barLayout=toolbar?.parent as AppBarLayout
                    barLayout.stateListAnimator=null
                }
            }
        }
        //menu
        if(getMenuResId()!=0){
            toolbar?.popupTheme=getMenuThemeResId()
            toolbar?.inflateMenu(getMenuResId())
            toolbar?.setOnMenuItemClickListener(this)
            setMenuMoreIconColor(toolbar,getMenuMoreIconColor())
        }

    }

    /**
     * 修改菜单上更多按钮(三个点)的颜色
     */
    private fun setMenuMoreIconColor(toolbar:Toolbar?,colorRes:Int) {
        if(toolbar==null){
            return
        }
        var iconMore=ContextCompat.getDrawable(toolbar.context,R.drawable.abc_ic_menu_overflow_material)
        if(iconMore!=null){
            iconMore.setColorFilter(ContextCompat.getColor(toolbar.context, colorRes), PorterDuff.Mode.SRC_ATOP);
            toolbar.overflowIcon=iconMore
        }
    }

    /**
     * 设置菜单角标 。
     *
     * 备注：需要在菜单中先设置 app:actionProviderClass="com.zz.libcore.widget.BadgeActionProvider"
     *
     * eg:
     * <item android:id="@+id/cat"
    app:showAsAction="ifRoom"
    android:orderInCategory="1"
    android:title=""
    app:actionProviderClass="com.zz.libcore.widget.BadgeActionProvider"
    />
     *
     */
    protected fun updateMenuIconBadge(@IdRes menuId:Int,@DrawableRes icon:Int,clickListener:OnClickListener){
        val menuItem = toolbar?.menu?.findItem(menuId)
        if(menuItem!=null){
            val actionProvider:BadgeActionProvider = MenuItemCompat.getActionProvider(menuItem) as BadgeActionProvider
            actionProvider.setIcon(icon)
            actionProvider.setOnClickListener(clickListener)
        }
    }
    protected fun updateMenuIconBadge(@IdRes menuId:Int,count:Int){
        val menuItem = toolbar?.menu?.findItem(menuId)
        if(menuItem!=null){
            val actionProvider:BadgeActionProvider = MenuItemCompat.getActionProvider(menuItem) as BadgeActionProvider
            actionProvider.setBadge(count)
        }
    }

    open fun onFinish(){
        finish()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_toolbar_base
    }
    /**
     * 返回图标
     */
    @DrawableRes
    open fun getNavigationIcon():Int=R.mipmap.ic_back

    /**
     * 上下滑动是否隐藏toolbar
     */
    open fun canToolbarScroll():Boolean=false

    /**
     * 标题
     */
    open fun getToolbarTitle():String=""

    /**
     * 是否显示阴影
     */
    open fun showToolbarShadow():Boolean=true

    @MenuRes
    open fun getMenuResId():Int=0

    /**
     * 菜单点击
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }

    /**
     * 菜单样式
     */
    @StyleRes
    open fun getMenuThemeResId():Int=R.style.PopupMenu

    @ColorRes
    open fun getMenuMoreIconColor():Int= R.color.black

}