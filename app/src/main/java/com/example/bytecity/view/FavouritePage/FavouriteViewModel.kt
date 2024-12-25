package com.example.bytecity.view.FavouritePage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteViewModel : ViewModel() {

    private val _favouriteState = mutableStateOf(FavouriteState())
    val favouriteState:State<FavouriteState> = _favouriteState

    init{
        getFavouriteProducts()
    }


    private fun getFavouriteProducts() {
        viewModelScope.launch {
            try {
                val resultSetProductIds = DbHelper.getAllProductWishList()
                if (!resultSetProductIds.isBeforeFirst) {
                    resultSetProductIds.close()
                    withContext(Dispatchers.Main) {
                        _favouriteState.value = _favouriteState.value.copy(
                            loading = false
                        )
                    }
                    return@launch
            }
            val productsInWishList = mutableListOf<Product>()
            while (resultSetProductIds.next()) {
                val id = resultSetProductIds.getInt("idProduct")
                val resultSetProduct = DbHelper.getProductById(id)
                resultSetProduct.next()

                val idDiscount = resultSetProduct.getInt("idDiscount")
                var discountValue = 0.0
                if(!resultSetProduct.wasNull()){
                    val resultSetDiscount = DbHelper.getInfoDiscount(idDiscount)
                    resultSetDiscount.next()
                    discountValue = resultSetDiscount.getDouble("value")
                    resultSetDiscount.close()
                }
                val product = Product.parse(resultSetProduct, discountValue)
                productsInWishList.add(product)
                resultSetProduct.close()
            }
            resultSetProductIds.close()
//            withContext(Dispatchers.Main){
                _favouriteState.value = _favouriteState.value.copy(
                    loading = false,
                    products = productsInWishList
                )
//            }
        }
            catch (e:Exception){
                _favouriteState.value = _favouriteState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }

    }

    fun deleteProduct(product:Product){
        val newProducts = _favouriteState.value.products - product
        _favouriteState.value = _favouriteState.value.copy(
            products = newProducts
        )
    }



    data class FavouriteState(
        var loading: Boolean = true,
        var error: String? = null,
        var products: List<Product> = listOf()
    )
}