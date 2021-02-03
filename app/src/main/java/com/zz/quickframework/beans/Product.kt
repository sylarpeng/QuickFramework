package com.zz.quickframework.beans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(var name:String): Parcelable {
}