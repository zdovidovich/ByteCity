package com.example.bytecity.businessClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductForCart(val product:Product, var qty:Int):Parcelable