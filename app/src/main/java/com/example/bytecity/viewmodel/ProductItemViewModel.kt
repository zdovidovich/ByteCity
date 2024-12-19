package com.example.bytecity.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.Db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductItemViewModel : ViewModel() {

    private val _productItemState = mutableStateOf(ProductItemState())
    val productItemState: State<ProductItemState> = _productItemState


    fun getRating(product: Product) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val resultSetRating = Db.getRating(product)
                if (resultSetRating.isBeforeFirst) {
                    var res = 0.0f
                    var qty = 0
                    while (resultSetRating.next()) {
                        res += resultSetRating.getFloat("res")
                        qty++
                    }
                    res /= qty
                    _productItemState.value = _productItemState.value.copy(
                        rating = String.format("%.2f", res).replace(",", ".").toFloat()
                    )
                }
                resultSetRating.close()

                _productItemState.value = _productItemState.value.copy(
                    loading = false
                )
            }

        } catch (ex: Exception) {
            _productItemState.value = _productItemState.value.copy(
                loading = false,
                error = ex.message
            )

        }
    }

    fun addWishList(product: Product): Int {
        try {
            val resultSetWishList = Db.getProductWishList(product)
            if (resultSetWishList.isBeforeFirst) {
                resultSetWishList.close()
                Db.deleteProductWishList(product)
                return 1 // DELETED
            }
            Db.addProductToWishList(product)
            resultSetWishList.close()
            return 0 //ADDED

        } catch (ex: Exception) {
            return -1 //ERROR
        }
    }


    fun addCart(product: Product): Int {
        if (product.inStock == 0) return 2 // NOT ON SALE

        try {
            val resultSetCart = Db.getProductCart(product)
            if (resultSetCart.isBeforeFirst) {
                resultSetCart.close()
                Db.deleteProductCart(product)
                return 1 // DELETED
            }
            Db.addProductToCart(product)
            resultSetCart.close()
            return 0 //ADDED

        } catch (ex: Exception) {
            return -1 //ERROR
        }
    }


    data class ProductItemState(
        var loading: Boolean = true,
        var rating: Float = 0f,
        var error: String? = null
    )
}