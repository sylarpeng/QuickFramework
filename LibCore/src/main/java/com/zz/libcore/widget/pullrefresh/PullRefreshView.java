package com.zz.libcore.widget.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

import com.google.android.material.appbar.AppBarLayout;
import com.zz.libcore.R;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/11/25 15:03
 * 类描述：
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
    protected Context context;
    private ConstraintLayout cslRoot;
    private FrameLayout flContentView;
    private FrameLayout flHeaderView;
    private View headerView;
    private View contentView;

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

    public PullRefreshView(@NonNull Context context) {
        this(context,null);
    }

    public PullRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setRefreshCallBack(RefreshStateChangeListener refreshCallBack) {
        this.refreshCallBack = refreshCallBack;
    }

    private void init(Context context) {
        this.context=context;
        maxHeaderHeight= context.getResources().getDimensionPixelSize(R.dimen.refresh_header_max_height);
    }
    private void initRefreshView(){
        this.removeAllViews();
        View.inflate(context, R.layout.refresh_rv,this);
        cslRoot=findViewById(R.id.csl_root);
        flContentView=findViewById(R.id.content_view);
        flHeaderView=findViewById(R.id.fl_header);
        ViewGroup.LayoutParams layoutParams = flHeaderView.getLayoutParams();
        layoutParams.height=maxHeaderHeight;
        flHeaderView.setLayoutParams(layoutParams);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("RefreshView can host only one direct child");
        }
        if(getChildCount()==1){
            View childAt = getChildAt(0);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if(childAt.getParent()!=null && childAt.getParent() instanceof ViewGroup){
                ((ViewGroup)childAt.getParent()).removeView(childAt);
                initRefreshView();
                addContentView(childAt,layoutParams);
            }
            appBarLayout=checkAppbarLayout(childAt);
            if(appBarLayout!=null){
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        appBarVerticalOffset=verticalOffset;
                    }
                });
            }
        }
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

    private void addContentView(View contentView,ViewGroup.LayoutParams layoutParams) {
        this.contentView=contentView;
        if(contentView!=null){
            this.flContentView.removeAllViews();
            this.flContentView.addView(contentView,layoutParams);
        }
    }

    public void setCustomRefreshView(View headerView){
        if(headerView!=null){
            this.headerView=headerView;
            this.flHeaderView.removeAllViews();
            this.flHeaderView.addView(headerView);
        }
    }
    float x,y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(refreshState==STATE_NONE || isRefreshing()){
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
        if(appBarLayout!=null){
            return appBarVerticalOffset==0;
        }else{
            return !contentView.canScrollVertically(-1);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
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
                showheaderHeight=0;
                constraintSetHeader(true);
                onCallBack(STATE_NONE);
            }
        },refreshCompletedDelayTime);
    }

    private void onCallBack(int refreshState){
        this.refreshState=refreshState;
        if(refreshCallBack==null || headerView==null){
            return;
        }
        switch (refreshState){
            case STATE_NONE:
                refreshCallBack.onRefreshNone();
                break;
            case STATE_PULL_DOWN:
                refreshCallBack.onPullDown(headerView,showheaderHeight,maxHeaderHeight);
                break;
            case STATE_REFRESHING:
                refreshCallBack.onRefresh(headerView);
                break;
            case STATE_COMPLETE:
                refreshCallBack.onRefreshComplete(headerView);
                break;
        }
    }

}
