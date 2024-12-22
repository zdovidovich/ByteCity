package com.example.bytecity.businessClasses

data class Review(
    val idReview: Int,
    val idProduct: Int,
    val idUser: Int,
    val login: String,
    val rating: Double,
    val review: String
)