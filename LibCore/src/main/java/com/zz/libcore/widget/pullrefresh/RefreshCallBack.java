package com.zz.libcore.widget.pullrefresh;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/28 10:30
 * 类描述：下拉刷新/加载更多 回调
 */
public interface RefreshCallBack {
    /**
     * 下拉刷新
     */
    void onRefresh();
    /**
     * 加载更多
     */
    void onLoadMoreData();
}
