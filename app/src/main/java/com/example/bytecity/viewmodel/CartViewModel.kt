package com.example.bytecity.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class CartViewModel : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    init {
        getProductsFromCart()
    }


    private fun getProductsFromCart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultSetProductIds = Db.getAllProductCart()
                if (!resultSetProductIds.isBeforeFirst) {
                    resultSetProductIds.close()
                    withContext(Dispatchers.Main) {
                        _cartState.value = _cartState.value.copy(
                            loading = false
                        )
                    }
                    return@launch
                }
                val productsInWishList = mutableListOf<ProductForCart>()
                while (resultSetProductIds.next()) {
                    val id = resultSetProductIds.getInt("idProduct")
                    val resultSetProduct = Db.getProductById(id)

                    resultSetProduct.next()
                    val inStock = resultSetProduct.getInt("inStock")

                    if(inStock == 0){
                        Db.deleteProductCart(id)
                        continue
                    }

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
                        idProduct = id,
                        brand = resultSetProduct.getString("brand"),
                        model = resultSetProduct.getString("model"),
                        type = resultSetProduct.getString("type"),
                        price =  String.format("%.2f", resultSetProduct.getDouble("price") * (1 - discountValue)).replace(",", ".").toDouble(),
                        imageProduct = resultSetProduct.getString("imageProduct"),
                        inStock = inStock,
                        releaseDate = rdate!!
                    )
                    val productForCart =
                        ProductForCart(product, resultSetProductIds.getInt("quantity"))
                    productsInWishList.add(productForCart)
                    resultSetProduct.close()
                }
                resultSetProductIds.close()
                withContext(Dispatchers.Main) {
                    _cartState.value = _cartState.value.copy(
                        loading = false,
                        products = productsInWishList
                    )
                }
            } catch (e: Exception) {
                _cartState.value = _cartState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun cleanCart() {
        viewModelScope.launch(Dispatchers.IO) {
            Db.cleanCart()
        }
        //TODO MAYBE ADD TRY EXCEPT????
    }


    data class CartState(
        var loading: Boolean = true,
        var error: String? = null,
        var products: MutableList<ProductForCart> = mutableListOf()
    )


}