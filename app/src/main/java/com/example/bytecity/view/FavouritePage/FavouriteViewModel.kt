package com.example.bytecity.view.FavouritePage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.launch

class FavouriteViewModel : ViewModel() {

    private val _favouriteState = mutableStateOf(FavouriteState())
    val favouriteState: State<FavouriteState> = _favouriteState

    init {
        getFavouriteProducts()
    }


    private fun getFavouriteProducts() {
        viewModelScope.launch {
            try {
                val resultSetProductIds = DbHelper.getAllProductWishList()
                val productsInWishList = mutableListOf<Product>()
                while (resultSetProductIds.next()) {
                    productsInWishList.add(Product.parse(resultSetProductIds))
                }
                resultSetProductIds.close()
                _favouriteState.value = _favouriteState.value.copy(
                    loading = false,
                    products = productsInWishList
                )
            } catch (e: Exception) {
                _favouriteState.value = _favouriteState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }

    }

    fun deleteProduct(product: Product) {
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