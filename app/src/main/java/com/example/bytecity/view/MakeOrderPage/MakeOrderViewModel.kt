package com.example.bytecity.view.MakeOrderPage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.DbConnection
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Date
import java.time.LocalDate


class MakeOrderViewModel : ViewModel() {


    suspend fun makeOrder(
        context: Context,
        products: List<ProductForCart>,
    ): Int = withContext(Dispatchers.IO) {
        val connection = DbConnection()
        return@withContext try {
            connection.connect(context)
            connection.connection.autoCommit = false
            connection.connection.transactionIsolation =
                Connection.TRANSACTION_SERIALIZABLE

            val res = DbHelper.insertOrder(
                productsForCart = products,
                date = Date.valueOf(LocalDate.now().toString()),
                connection = connection
            )
            connection.connection.autoCommit = true

            if (res != 200) {
                connection.connection.close()
                return@withContext 2
            }
            DbHelper.cleanCart(connection)

            connection.connection.close()

            return@withContext 200

        } catch (ex: Exception) {
            Log.d(ex.message, ex.message.toString())
            connection.connection.rollback()
            connection.connection.autoCommit = true
            connection.connection.close()
            1 // bad
        }
    }
}