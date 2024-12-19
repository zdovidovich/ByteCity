package com.example.bytecity.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.Locale

class AllOrderPageViewModel : ViewModel() {

    private val _orderState = mutableStateOf(OrderState())
    val orderState: State<OrderState> = _orderState

    init {
        getProducts()
    }


    private fun getProducts(): Int {
        val orders: MutableList<Order> = mutableListOf()
        var resultSetOrdersId: ResultSet? = null
        var resultSetOrderDetails: List<ResultSet?> = listOf()
        var resultSetOrderProducts: List<ResultSet?> = listOf()

        return try {
            viewModelScope.launch(Dispatchers.IO) {
                resultSetOrdersId = Db.getOrdersId()
                resultSetOrderDetails = Db.getOrderDetails(resultSetOrdersId!!)
                resultSetOrdersId?.close()
                resultSetOrdersId = Db.getOrdersId()
                resultSetOrderProducts = Db.getOrderProducts(resultSetOrdersId!!)
                for (i in resultSetOrderDetails.indices) {
                    resultSetOrderDetails[i]?.next()
                    val name = resultSetOrderDetails[i]?.getString("name")
                    val status = resultSetOrderDetails[i]?.getString("status")
                    val registrationDate = resultSetOrderDetails[i]?.getDate("registrationDate")
                    val productsTemp = mutableListOf<ProductForCart>()
                    while(resultSetOrderProducts[i]?.next()!!){
                        val resultSetProduct = Db.getProductById(resultSetOrderProducts[i]?.getInt("idProduct")!!)
                        resultSetProduct.next()

                        val idDiscount = resultSetProduct.getInt("idDiscount")
                        var discountValue = 0.0
                        if(!resultSetProduct.wasNull()){
                            val resultSetDiscount = Db.getInfoDiscount(idDiscount)
                            resultSetDiscount.next()
                            discountValue = resultSetDiscount.getDouble("value")
                            resultSetDiscount.close()
                        }


                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                        val rdate = formatter.parse(resultSetProduct.getDate("releaseDate").toString())
                        val product = Product(
                            idProduct = resultSetProduct.getInt("idProduct"),
                            brand = resultSetProduct.getString("brand"),
                            model = resultSetProduct.getString("model"),
                            type = resultSetProduct.getString("type"),
                            price = String.format("%.2f", resultSetProduct.getDouble("price") * (1 - discountValue)).replace(",", ".").toDouble(),
                            imageProduct = resultSetProduct.getString("imageProduct"),
                            inStock = resultSetProduct.getInt("inStock"),
                            releaseDate = rdate!!
                        )
                        val qty = resultSetOrderProducts[i]?.getInt("quantity")!!
                        productsTemp.add(ProductForCart(product, qty))
                        resultSetProduct.close()
                    }
                    orders.add(Order(name=name!!, products= productsTemp, status = status!!, registrationDate = registrationDate!!))
                }

                _orderState.value = _orderState.value.copy(
                    loading = false,
                    orders = orders
                )
            }
            200
        } catch (ex: Exception) {
            _orderState.value = _orderState.value.copy(
                loading = false,
                error = ex.message
            )
            1 // smth's bad
        } finally {
            resultSetOrdersId?.close()
            for (i in resultSetOrderDetails) {
                i?.close()
            }
            for (i in resultSetOrderProducts) {
                i?.close()
            }
        }
    }


    data class OrderState(
        var loading: Boolean = true,
        var error: String? = null,
        var orders: List<Order> = listOf()
    )

}