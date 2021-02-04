package com.zz.libcore.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.zz.libcore.R

/**
 * 带角标的textView
 */
class BadgeTextView(context: Context, attrs: AttributeSet?, defAttrStyle: Int = 0) : AppCompatTextView(context, attrs,defAttrStyle) {
    private var paint:Paint = Paint()
    private val BADGE_MAX_RADIUS=100
    private val BADGE_MIN_RADIUS=10

    /**
     * 圆点半径
     */
    private var radius=36f

    /**
     * BadgeTextView 宽度
     */
    private var viewWidth=0
    /**
     * BadgeTextView 高度
     */
    private var viewHeight=0

    /**
     * 角标背景色
     */
    private var badgeBgColor:Int=Color.RED

    /**
     * 角标文字颜色
     */
    private var badgeTxColor:Int=Color.WHITE

    /**
     * 角标文字大小
     */
    private var badgeTxSize:Float=36f

    /**
     * 是否显示角标
     */
    private var showBadge:Boolean=false

    /**
     * 角标内容
     */
    private var badgeValue:String?=null
    private var pointX=0f
    private var pointY=0f

    init {
        initPaint()
        initAttr(context,attrs,defAttrStyle)
        if(paddingEnd<radius*2){
            setPaddingRelative(paddingStart, paddingTop, radius.toInt() * 2, paddingBottom)
        }
    }
    constructor(context: Context):this(context,null)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)

    private fun initAttr(context: Context, attrs: AttributeSet?,defAttrStyle: Int) {
        val array=context.obtainStyledAttributes(attrs, R.styleable.BadgeText,defAttrStyle, 0)
        (0..array.indexCount)
            .asSequence()
            .map { array.getIndex(it) }
            .forEach {
                when(it){
                    R.styleable.BadgeText_badgeBgColor->{
                        badgeBgColor = array.getColor(it,Color.RED)}
                    R.styleable.BadgeText_badgeRadius->{
                        radius = array.getDimension(it, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics))}
                    R.styleable.BadgeText_badgeTxColor->{
                        badgeTxColor = array.getColor(it,Color.WHITE)}
                    R.styleable.BadgeText_badgeTxSize->{
                        badgeTxSize = array.getDimension(it,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, resources.displayMetrics))}
                }
            }
        array.recycle()



    }

    private fun initPaint() {
        paint.color= Color.RED
        paint.style= Paint.Style.FILL
        paint.isAntiAlias=true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(showBadge){
            canvas?.let {
                drawBg(it)
                drawText(it)
            }
        }

    }
    private fun drawBg(canvas: Canvas){
        pointX= viewWidth-radius
        pointY=radius
        paint.color= badgeBgColor
        canvas.drawCircle(pointX, pointY, radius, paint)
    }
    private fun drawText(canvas: Canvas){
        badgeValue?.let {
            paint.color= badgeTxColor
            paint.textSize=badgeTxSize
            val textWidth=paint.measureText(it)
            val textX=pointX-textWidth/2
            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val dy: Float = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
            val textY = pointY + dy
            canvas.drawText(it, textX, textY, paint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth=w
        viewHeight=h
    }

    fun show(){
        showBadge=true
        invalidate()
    }
    fun show(value:String){
        showBadge=value.isNotEmpty()
        badgeValue=value
        invalidate()
    }
    fun hide(){
        showBadge=false
        invalidate()
    }
}