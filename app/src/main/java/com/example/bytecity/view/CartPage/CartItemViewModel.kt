package com.example.bytecity.view.CartPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartItemViewModel : ViewModel() {

    fun addOneProduct(
        productForCart: ProductForCart,
        frontAddOneProduct: () -> Unit
    ): Int {
        if (productForCart.product.inStock == productForCart.qty) {
            return 1 // Больше некуда
        }
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                DbHelper.updateProductCartPlusQty(productForCart.product)
            }
            frontAddOneProduct()
            200 //That's good
        } catch(ex:Exception){
            2 // SQL не рада
        }
    }

    fun removeOneProduct(
        productForCart: ProductForCart,
        frontRemoveOneProduct: () -> Unit
    ): Int {
        if (productForCart.qty == 1) {
            return 1 // Меньше некуда
        }
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                DbHelper.updateProductCartMinusQty(productForCart.product)
            }
            frontRemoveOneProduct()
            200 // That's good
        } catch(ex:Exception){
            2 // SQL не рада
        }
    }

    fun removeProductFromCart(
        productForCart:ProductForCart,
        frontRemoveProductFromCart: () -> Unit
    ): Int{
        return try{
            viewModelScope.launch(Dispatchers.IO) {
                DbHelper.deleteProductCart(productForCart.product)
                frontRemoveProductFromCart()
            }
            200 // That's good
        }
        catch(ex:Exception){
            1 // Sql ошибка
        }
    }


}