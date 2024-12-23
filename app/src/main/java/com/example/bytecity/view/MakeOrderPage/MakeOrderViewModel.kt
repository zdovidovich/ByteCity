package com.example.bytecity.view.MakeOrderPage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Db
import com.example.bytecity.model.DbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Date
import java.time.LocalDate


class MakeOrderViewModel : ViewModel() {


    suspend fun makeOrder(
        context: Context,
        products: List<ProductForCart>,
    ): Int {
        val connection = DbConnection()
        return try {
            val res: Int
            withContext(Dispatchers.IO) {
                connection.connect(context)
                connection.connection.autoCommit = false
                connection.connection.transactionIsolation =
                    Connection.TRANSACTION_REPEATABLE_READ
                res = Db.insertOrder(
                    productsForCart = products,
                    date = Date.valueOf(LocalDate.now().toString()),
                    connection = connection
                )
                connection.connection.autoCommit = true

            }
            if (res != 200) {
                connection.connection.close()
                return 2
            }
            withContext(Dispatchers.IO) {
                Db.cleanCart(connection)
            }
            connection.connection.close()
            return 200
        } catch (ex: Exception) {
            Log.d(ex.message, ex.message.toString())
            connection.connection.rollback()
            connection.connection.autoCommit = true
            connection.connection.close()
            1 // bad
        }
    }
}