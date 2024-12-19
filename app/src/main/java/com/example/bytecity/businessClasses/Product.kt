package com.example.bytecity.businessClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Product(
    val idProduct: Int,
    val brand: String,
    val model: String,
    val type: String,
    val price: Double,
    val imageProduct: String,
    val inStock: Int,
    val releaseDate: Date
    ) : Parcelable