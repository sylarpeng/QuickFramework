package com.zz.quickframework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/12/11 10:15
 * 类描述：
 */
public class CustomView extends View {
    String tag="CustomView";
    Paint paint;

    int width;
    int height;
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint=new Paint();
        paint.setAntiAlias(true);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log("onDraw");
        drawBg(canvas);
        drawSecondProgress(canvas);
//        drawProgress(canvas);
    }
    private void drawBg(Canvas canvas){
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        RectF r2=new RectF();                           //RectF对象
        r2.left=0;                                 //左边
        r2.top=0;                                 //上边
        r2.right=width;                                   //右边
        r2.bottom=height;                              //下边
        canvas.drawRoundRect(r2, height/2, height/2, paint);        //绘制圆角矩形
    }

    private void drawProgress(Canvas canvas){
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        RectF r2=new RectF();                           //RectF对象
        r2.left=0;                                 //左边
        r2.top=0;                                 //上边
        r2.right=(float) (width*0.5);                                        //右边
        r2.bottom=height;                              //下边
        canvas.drawRoundRect(r2, height/2, height/2, paint);        //绘制圆角矩形
    }
    private void drawSecondProgress(Canvas canvas){
        paint.setColor(Color.WHITE);
        paint.setShader(getLinearGradient());
        paint.setStyle(Paint.Style.FILL);
        RectF r2=new RectF();                           //RectF对象
        r2.left=0;                                 //左边
        r2.top=0;                                 //上边
        r2.right= (float) (width*0.8);                                   //右边
        r2.bottom=height;                              //下边
        canvas.drawRoundRect(r2, height/2, height/2, paint);        //绘制圆角矩形
    }

    private LinearGradient getLinearGradient(){
        int[] fgColorList=new int[]{Color.RED,Color.BLUE};
        return new LinearGradient(0, 0, getWidth(), height, fgColorList, null, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        log("onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width=w;
        this.height=h;
        log("onSizeChanged,w="+w+"h="+h);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        log("onFinishInflate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        log("onMeasure,widthSpecMode="+getMode(widthSpecMode)+",widthSpecSize="+widthSpecSize);
        log("onMeasure,heightSpecMode="+getMode(heightSpecMode)+",heightSpecSize="+heightSpecSize);
        setMeasuredDimension(1000,100);
    }

    private void log(String msg){
        Log.d(tag,msg);
    }
    private String getMode(int mode){
        String modeStr=null;
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                modeStr="UNSPECIFIED";
                break;
            case MeasureSpec.AT_MOST:
                modeStr="AT_MOST";
                break;
            case MeasureSpec.EXACTLY:
                modeStr="EXACTLY";
                break;
        }
        return modeStr;
    }
}
