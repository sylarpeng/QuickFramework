package com.zz.quickframework

import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import com.zz.libcore.ui.ZBaseFragment
import com.zz.myapplication1.R
import com.zz.myapplication1.databinding.ActivityMain5Binding

/**
 * 项目名称:Rosegal
 * 创建人：Created by  pzj
 * 创建时间:2020/12/9 17:36
 * 类描述：
 */
class Fragment5 : RGFragment<ActivityMain5Binding>(R.layout.activity_main5) {
    var start:Boolean =true;
    var motionLayout:MotionLayout?=null;
    var seekBar:SeekBar?=null


    override fun bindView(contentView: View): ActivityMain5Binding? {
        return ActivityMain5Binding.bind(contentView)
    }

    override fun initView(view: View) {
        motionLayout=bind.mlRoot
        seekBar=bind.seekbar
        bind.actor.setOnClickListener(View.OnClickListener {
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