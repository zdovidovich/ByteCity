package com.example.bytecity.view.CartPage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    init{
        getProductsFromCart()
    }

    private fun getProductsFromCart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultSetProductIds = DbHelper.getAllProductCart()
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
                    val resultSetProduct = DbHelper.getProductById(id)
                    resultSetProduct.next()
                    var qty = resultSetProductIds.getInt("quantity")
                    val inStock = resultSetProduct.getInt("inStock")
                    if(qty > inStock){
                        qty = 1
                        DbHelper.updateQtyCartToOne(idProduct = id)
                    }
                    if(inStock == 0){
                        DbHelper.deleteProductCart(id)
                        continue
                    }

                    val idDiscount = resultSetProduct.getInt("idDiscount")
                    var discountValue = 0.0
                    if(!resultSetProduct.wasNull()){
                        val resultSetDiscount = DbHelper.getInfoDiscount(idDiscount)
                        resultSetDiscount.next()
                        discountValue = resultSetDiscount.getDouble("value")
                        resultSetDiscount.close()
                    }

                    val product = Product.parse(resultSetProduct, discountValue)
                    val productForCart =
                        ProductForCart(product, qty)
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
            DbHelper.cleanCart()
            _cartState.value = _cartState.value.copy(
                products = listOf()
            )
        }
        //TODO MAYBE ADD TRY EXCEPT????
    }

    fun plusProduct(product: ProductForCart){
        val editedProductForCart = _cartState.value.products.find { it.product.idProduct == product.product.idProduct }
        editedProductForCart?.let {
            val updatedProductForCart = it.copy(qty = it.qty + 1)
            val newProductsForCart =
                _cartState.value.products.map { productForCart -> if (productForCart.product.idProduct == updatedProductForCart.product.idProduct) updatedProductForCart else productForCart }
            _cartState.value = _cartState.value.copy(
                products = newProductsForCart
            )
        }
    }

    fun minusProduct(product: ProductForCart){
        val editedProductForCart = _cartState.value.products.find { it.product.idProduct == product.product.idProduct }
        editedProductForCart?.let {
            val updatedProductForCart = it.copy(qty = it.qty - 1)
            val newProductsForCart =
                _cartState.value.products.map { productForCart -> if (productForCart.product.idProduct == updatedProductForCart.product.idProduct) updatedProductForCart else productForCart }
            _cartState.value = _cartState.value.copy(
                products = newProductsForCart
            )
        }
    }

    fun deleteProduct(product: ProductForCart){
        val newProducts = _cartState.value.products - product
        _cartState.value = _cartState.value.copy(
            products = newProducts
        )
    }




    data class CartState(
        var loading: Boolean = true,
        var error: String? = null,
        var products: List<ProductForCart> = listOf()
    )


}