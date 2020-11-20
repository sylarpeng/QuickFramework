package com.zz.libcore.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.view.ActionProvider;


import com.zz.libcore.R;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/20 15:51
 * 类描述：
 */
public class BadgeActionProvider extends ActionProvider {
    private Context context;
    private ImageView mIvIcon;
    private TextView mTvBadge;

    private View.OnClickListener mOnClickListener;

    public BadgeActionProvider(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public View onCreateActionView() {
        //读取support下Toolbar/ActionBar的高度，为了让这个Menu高和宽和系统的menu达到一致。
        int size =context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(size, size);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.menu_badge_provider, null, false);
        view.setLayoutParams(params);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvBadge = view.findViewById(R.id.tv_badge);
        view.setOnClickListener(new BadgeMenuClickListener());
        return view;
    }

    /**
     * 设置图标。
     *
     * @param icon drawable 或者mipmap中的id。
     */
    public void setIcon(@DrawableRes int icon) {
        mIvIcon.setImageResource(icon);
    }

    /**
     * 设置显示的数字。
     *
     * @param i 数字。
     */
    public void setBadge(int i) {
        mTvBadge.setText(formatNum(i));
    }

    public String formatNum(int i){
        mTvBadge.setVisibility(i>0?View.VISIBLE:View.GONE);
        String num=Integer.toString(i);
        if(i>99){
           num="99+";
        }
        return num;
    }

    /**
     * 设置文字。
     *
     * @param i string.xml中的id。
     */
    public void setTextInt(@StringRes int i) {
        mTvBadge.setText(i);
    }

    /**
     * 设置显示的文字。
     *
     * @param i 文字。
     */
    public void setText(CharSequence i) {
        mTvBadge.setText(i);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private class BadgeMenuClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(v);
            }
        }
    }
}