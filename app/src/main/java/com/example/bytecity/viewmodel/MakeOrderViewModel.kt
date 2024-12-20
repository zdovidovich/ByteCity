package com.example.bytecity.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Db
import com.example.bytecity.model.DbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.sql.Connection
import java.sql.Date
import java.time.LocalDate


class MakeOrderViewModel : ViewModel() {


    fun makeOrder(
        context: Context,
        products: List<ProductForCart>,
        name: String
    ): Int {
        val connection = DbConnection()

        return try {
            if (name.isEmpty()) {
                2 // bad name
            } else {
                runBlocking {
                    viewModelScope.launch(Dispatchers.IO) {
                        connection.connect(context)
                        connection.connection.autoCommit = false
                        connection.connection.transactionIsolation =
                            Connection.TRANSACTION_REPEATABLE_READ
                        Db.insertOrder(
                            productsForCart = products,
                            name = name.trim(),
                            date = Date.valueOf(LocalDate.now().toString()),
                            connection = connection
                        )
                        connection.connection.autoCommit = true
                        connection.connection.transactionIsolation =
                            Connection.TRANSACTION_READ_COMMITTED
                        connection.connection.close()
                    }
                }
                200
            }
        } catch (ex: Exception) {

            Log.d(ex.message, ex.message.toString())
            connection.connection.rollback()
            connection.connection.autoCommit = true
            connection.connection.transactionIsolation = Connection.TRANSACTION_READ_COMMITTED
            connection.connection.close()
            1 // bad
        }
    }
}