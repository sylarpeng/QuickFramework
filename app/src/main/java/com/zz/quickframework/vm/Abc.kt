package com.zz.quickframework.vm


import java.io.Serializable

class Abc private constructor() : Serializable {
    companion object {
        private var instance: Abc? = null
        get() {
            if (field == null) {
                field = Abc()
            }
            return field
        }

        @Synchronized //添加synchronized同步锁
        fun get(): Abc {
            return instance!!
        }
    }

    //防止单例对象在反序列化时重新生成对象
    private fun readResolve(): Any {
        return get()
    }
}