package com.example.bytecity.businessClasses

import android.os.Parcelable
import com.example.bytecity.model.DbConnection
import com.example.bytecity.model.DbHelper
import kotlinx.parcelize.Parcelize
import java.sql.ResultSet
import java.util.Date

@Parcelize
data class Order(
    val registrationDate: Date,
    val status: String,
    val products: List<ProductForCart>
) : Parcelable {

    companion object {

        suspend fun parse(resultSet: ResultSet, connection: DbConnection): Order {
            val resultSetOrderProducts =
                DbHelper.getOrderProducts(idOrders = resultSet.getInt("idOrder"))
            val status = resultSet.getString("status")
            val registrationDate = resultSet.getDate("registrationDate")
            val productsTemp = mutableListOf<ProductForCart>()
            while (resultSetOrderProducts.next()) {
                productsTemp.add(ProductForCart.parse(resultSetOrderProducts, connection))
            }
            resultSetOrderProducts.close()
            return Order(
                registrationDate = registrationDate,
                status = status,
                products = productsTemp
            )
        }
    }

}