package com.example.bytecity.businessClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Order(
    val name:String,
    val registrationDate: Date,
    val status: String,
    val products: List<ProductForCart>
) : Parcelable