package com.zz.libcore.widget.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.transition.TransitionManager;

import com.google.android.material.appbar.AppBarLayout;
import com.zz.libcore.R;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/25 15:03
 * 类描述：下拉刷新view
 */
public class PullRefreshView extends ConstraintLayout {
    /**
     * 初始状态
     */
    public static final int STATE_NONE=-1;
    /**
     * 下拉中
     */
    public static final int STATE_PULL_DOWN=0;
    /**
     * 加载中
     */
    public static final int STATE_REFRESHING=1;
    /**
     * 加载完成
     */
    public static final int STATE_COMPLETE=2;
    private static final String TAG = "PullRefreshView";
    protected Context context;
    private ConstraintLayout cslRoot;
    private FrameLayout flContentView;
    private FrameLayout flHeaderView;
    private View headerView;
    private View contentView;
    /**
     * 滑动视图，当前支持RecyclerView,NestedScrollView,ScrollView
     */
    private View scrollView;

    private int speed=3;
    private ConstraintSet constraintSet;
    /**
     * 下拉头部最大高度
     */
    private int maxHeaderHeight;
    //下拉头部显示部分的高度
    private int showheaderHeight;

    private int refreshState=STATE_NONE;
    private RefreshStateChangeListener refreshCallBack;

    /**
     * 调用刷新完成后，停止N时间后恢复初始化状态
     */
    private int refreshCompletedDelayTime=1000;

    /**
     * 包含appBarLayout时通过 AppBarLayout的verticalOffset是否在滑动到顶部
     */
    private AppBarLayout appBarLayout;
    private int appBarVerticalOffset;

    /**
     * 是否使用原生SwipeRefreshLayout下拉组件
     */
    private boolean swipeRefreshEnable;
    private SwipeRefreshLayout mSwipeLayout;

    public PullRefreshView(@NonNull Context context) {
        this(context,null);
    }

    public PullRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        maxHeaderHeight= context.getResources().getDimensionPixelSize(R.dimen.refresh_header_max_height);
    }

    public PullRefreshView setHeaderView(View headerView){
        this.headerView=headerView;
        return this;
    }
    public PullRefreshView setContentView(View contentView){
        this.contentView=contentView;
        return this;
    }
    public PullRefreshView setRefreshCallBack(RefreshStateChangeListener refreshCallBack) {
        this.refreshCallBack = refreshCallBack;
        return this;
    }

    public PullRefreshView setSwipeRefreshEnable(boolean swipeRefreshEnable,@ColorInt int... colors) {
        this.swipeRefreshEnable = swipeRefreshEnable;
        if(swipeRefreshEnable){
            mSwipeLayout=new SwipeRefreshLayout(context);
            mSwipeLayout.setColorSchemeColors(colors);
            mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    PullRefreshView.this.onRefresh();
                }
            });
        }
        return this;
    }

    public void create(){
        this.removeAllViews();
        View.inflate(context, R.layout.refresh_rv,this);
        cslRoot=findViewById(R.id.csl_root);
        flContentView=findViewById(R.id.content_view);
        flHeaderView=findViewById(R.id.fl_header);
        ViewGroup.LayoutParams layoutParams = flHeaderView.getLayoutParams();
        layoutParams.height=maxHeaderHeight;
        flHeaderView.setLayoutParams(layoutParams);
        if(!swipeRefreshEnable){
            addHeaderView(headerView);
        }
        addContentView(contentView);
    }


    /**
     * 设置下拉刷新内容视图
     * @param contentView
     */
    private void addContentView(View contentView){
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        if(contentView.getParent()!=null && contentView.getParent() instanceof ViewGroup){
            ((ViewGroup)contentView.getParent()).removeView(contentView);
        }
        addContentView(contentView,layoutParams);
        appBarLayout=checkAppbarLayout(contentView);
        scrollView=checkScrollView(contentView);
        if(appBarLayout!=null){
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    appBarVerticalOffset=verticalOffset;
                }
            });
        }
    }
    private void addContentView(View contentView,ViewGroup.LayoutParams layoutParams) {
        this.contentView=contentView;
        if(contentView!=null){
            this.flContentView.removeAllViews();
            if(swipeRefreshEnable){
                addContentView(mSwipeLayout,contentView,layoutParams);
                this.flContentView.addView(mSwipeLayout);
            }else{
                addContentView(flContentView,contentView,layoutParams);
            }
        }
    }
    private void addContentView(ViewGroup viewGroup,View child,ViewGroup.LayoutParams layoutParams){
        if(layoutParams==null){
            viewGroup.addView(child);
        }else{
            viewGroup.addView(child,layoutParams);
        }
    }
    /**
     * 设置下拉刷新header视图
     * @param headerView
     */
    private void addHeaderView(View headerView){
        if(headerView!=null){
            this.flHeaderView.removeAllViews();
            this.flHeaderView.addView(headerView);
        }
    }
    float x,y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!swipeRefreshEnable && refreshState==STATE_NONE || isRefreshing()){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x=ev.getX();
                    y=ev.getY();
                    return false;
                case MotionEvent.ACTION_MOVE:
                    if(ev.getY()>y && (Math.abs(ev.getX()-x)<Math.abs(ev.getY()-y)) && isTop()){
                        return true;
                    }
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }else{
            if(swipeRefreshEnable){
                mSwipeLayout.setEnabled(isTop());
            }

            return super.onInterceptTouchEvent(ev);
        }

    }

    /**
     * 是否已滑动到顶部
     * @return
     */
    protected boolean isTop(){
        if(contentView==null){
            return false;
        }
        if(scrollView==null){
            return appBarVerticalOffset==0 && !contentView.canScrollVertically(-1);
        }else{
            return appBarVerticalOffset==0 && !scrollView.canScrollVertically(-1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(swipeRefreshEnable){
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int distance= (int) (ev.getY()-this.y);
                if(isRefreshing()){
                    updateHeaderView(maxHeaderHeight+(distance/speed));
                }else{
                    updateHeaderView(distance/speed);
                    onCallBack(STATE_PULL_DOWN);
                }

                return true;
            case MotionEvent.ACTION_UP:
                resetHeader();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isRefreshing(){
        return refreshState==STATE_REFRESHING;
    }

    private void updateHeaderView(int moveY){
        showheaderHeight=moveY;
        if(showheaderHeight<0){
            showheaderHeight=0;
        }
        constraintSetHeader(false);
    }

    private void resetHeader(){
        if(showheaderHeight>=maxHeaderHeight){
            //下拉刷新
            showheaderHeight=maxHeaderHeight;
            if(!isRefreshing()){
                onRefresh();
            }

        }else{
            //还原
            showheaderHeight=0;
            onCallBack(STATE_NONE);
        }
        constraintSetHeader(true);
    }

    private void constraintSetHeader(boolean animator) {
        if(constraintSet==null){
            constraintSet=new ConstraintSet();
            constraintSet.clone(cslRoot);
        }
        constraintSet.connect(R.id.anchor,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,showheaderHeight);
        if(animator){
            TransitionManager.beginDelayedTransition(cslRoot);
        }
        constraintSet.applyTo(cslRoot);
    }

    /**
     * 下拉刷新中
     */
    private void onRefresh(){
        onCallBack(STATE_REFRESHING);
    }
    /**
     * 下拉刷新完成
     */
    public void onRefreshCompleted(){
        onCallBack(STATE_COMPLETE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if(swipeRefreshEnable){
                    mSwipeLayout.setRefreshing(false);
                }else{
                    showheaderHeight=0;
                    constraintSetHeader(true);
                }
                onCallBack(STATE_NONE);
            }
        },refreshCompletedDelayTime);
    }

    private void onCallBack(int refreshState){
        this.refreshState=refreshState;
        if(refreshCallBack==null || headerView==null){
            return;
        }
        String status="";
        switch (refreshState){
            case STATE_NONE:
                refreshCallBack.onRefreshNone();
                status="onRefreshNone";
                break;
            case STATE_PULL_DOWN:
                refreshCallBack.onPullDown(headerView,showheaderHeight,maxHeaderHeight);
                status="onPullDown";
                break;
            case STATE_REFRESHING:
                refreshCallBack.onRefresh(headerView);
                status="onRefresh...";
                break;
            case STATE_COMPLETE:
                refreshCallBack.onRefreshComplete(headerView);
                status="onRefreshComplete";
                break;
        }
        Log.d(TAG,status);
    }


    private AppBarLayout checkAppbarLayout(View rootView){
        AppBarLayout appBar=null;
        if(rootView==null){
            return null;
        }
        if(rootView instanceof AppBarLayout){
            appBar= (AppBarLayout) rootView;
        }else if(rootView instanceof ViewGroup){
            int childCount = ((ViewGroup) rootView).getChildCount();
            if(childCount>0){
                for(int i=0;i<childCount;i++){
                    appBar=checkAppbarLayout(((ViewGroup) rootView).getChildAt(i));
                    if(appBar!=null){
                        break;
                    }
                }
            }
        }
        return appBar;
    }

    private View checkScrollView(View rootView){
        View scrollView=null;
        if(rootView==null){
            return null;
        }
        if(rootView instanceof RecyclerView || rootView instanceof NestedScrollView || rootView instanceof ScrollView){
            scrollView=rootView;
        }else if(rootView instanceof ViewGroup){
            int childCount = ((ViewGroup) rootView).getChildCount();
            if(childCount>0){
                for(int i=0;i<childCount;i++){
                    scrollView=checkScrollView(((ViewGroup) rootView).getChildAt(i));
                    if(scrollView!=null){
                        break;
                    }
                }
            }
        }
        return scrollView;
    }

}
