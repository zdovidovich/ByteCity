package com.example.bytecity.businessClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    ) : Parcelable {

        companion object{

            fun parse(resultSetProduct: ResultSet, discountValue:Double) : Product{
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val rdate = formatter.parse(resultSetProduct.getDate("releaseDate").toString())
                return Product(
                    idProduct = resultSetProduct.getInt("idProduct"),
                    brand = resultSetProduct.getString("brand"),
                    model = resultSetProduct.getString("model"),
                    type = resultSetProduct.getString("type"),
                    price = String.format("%.2f", resultSetProduct.getDouble("price") * (1 - discountValue)).replace(",", ".").toDouble(),
                    imageProduct = resultSetProduct.getString("imageProduct"),
                    inStock = resultSetProduct.getInt("inStock"),
                    releaseDate = rdate!!
                )
            }

        }
    }