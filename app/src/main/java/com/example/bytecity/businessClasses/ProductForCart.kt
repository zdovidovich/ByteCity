package com.example.bytecity.businessClasses

import android.os.Parcelable
import com.example.bytecity.model.DbConnection
import com.example.bytecity.model.DbHelper
import kotlinx.parcelize.Parcelize
import java.sql.ResultSet

@Parcelize
data class ProductForCart(val product: Product, var qty: Int) : Parcelable {

    companion object {

        suspend fun parse(resultSet: ResultSet, connection: DbConnection): ProductForCart {
            val resultSetProduct =
                DbHelper.getProductById(resultSet.getInt("idProduct"), connection)
            resultSetProduct.next()
            val product = Product.parse(resultSetProduct)
            val qty = resultSet.getInt("quantity")
            resultSetProduct.close()
            return ProductForCart(product, qty)
        }
    }

}