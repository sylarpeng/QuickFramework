package com.zz.quickframework

import android.transition.Transition
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import com.zz.libcore.ui.ZBaseFragment
import com.zz.myapplication1.R

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/12/9 17:36
 * 类描述：
 */
class Fragment5 : ZBaseFragment() {
    var start:Boolean =true;
    var motionLayout:MotionLayout?=null;
    var seekBar:SeekBar?=null;
    override fun getLayoutResId(): Int {
        return R.layout.activity_main5
    }

    override fun initView(view: View) {
        motionLayout=view.findViewById(R.id.ml_root)
        seekBar=view.findViewById(R.id.seekbar)
        view.findViewById<TextView>(R.id.actor).setOnClickListener(View.OnClickListener {
            if(start){
                motionLayout?.transitionToEnd()
            }else{
                motionLayout?.transitionToStart()
            }
            start=!start

        })
        motionLayout?.setTransitionListener(object:TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                seekBar?.progress=0
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                seekBar?.progress= (p3*100).toInt()
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                seekBar?.progress=if(start) 0 else 100
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                seekBar?.progress=100
            }

        })
    }
}