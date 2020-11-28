package com.zz.libcore.widget.pullrefresh;

import android.view.View;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/27 10:47
 * 类描述：下拉组件状态变化回调
 */
public interface RefreshStateChangeListener {
    /**
     * 下拉中回调
     * @param view 刷新头部view
     * @param dy 下拉距离
     * @param totalDy 可以下拉的总距离
     */
    void onPullDown(View view, int dy, int totalDy);

    /**
     * 松手后刷新中
     */
    void onRefresh(View view);

    /**
     * 刷新完成-开始隐藏头部
     * @param view
     */
    void onRefreshComplete(View view);

    /**
     * 刷新完成-隐藏头部结束(恢复到初始化状态)
     */
    void onRefreshNone();
}
