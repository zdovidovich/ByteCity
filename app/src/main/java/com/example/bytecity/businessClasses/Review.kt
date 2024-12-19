package com.example.bytecity.businessClasses

data class Review(val idReview: Int,
    val idProduct:Int,
    val idUser:Int,
    val rating:Double,
    val review:String
)