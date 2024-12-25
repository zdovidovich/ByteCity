package com.example.bytecity.view.CartPage

import androidx.lifecycle.ViewModel
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.DbHelper

class CartItemViewModel : ViewModel() {

    suspend fun addOneProduct(
        productForCart: ProductForCart,
        frontAddOneProduct: () -> Unit
    ): Int {
        if (productForCart.product.inStock == productForCart.qty) {
            return 1 // Больше некуда
        }
        return try {
            DbHelper.updateProductCartPlusQty(productForCart.product)
            frontAddOneProduct()
            200 //That's good
        } catch (ex: Exception) {
            2 // SQL не рада
        }
    }

    suspend fun removeOneProduct(
        productForCart: ProductForCart,
        frontRemoveOneProduct: () -> Unit
    ): Int {
        if (productForCart.qty == 1) {
            return 1 // Меньше некуда
        }
        return try {
            DbHelper.updateProductCartMinusQty(productForCart.product)
            frontRemoveOneProduct()
            200 // That's good
        } catch (ex: Exception) {
            2 // SQL не рада
        }
    }

    suspend fun removeProductFromCart(
        productForCart: ProductForCart,
        frontRemoveProductFromCart: () -> Unit
    ): Int {
        return try {
            DbHelper.deleteProductCart(productForCart.product)
            frontRemoveProductFromCart()
            200 // That's good
        } catch (ex: Exception) {
            1 // Sql ошибка
        }
    }
}