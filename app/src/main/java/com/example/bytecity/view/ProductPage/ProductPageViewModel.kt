package com.example.bytecity.view.ProductPage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.DbHelper
import com.example.bytecity.model.User

class ProductPageViewModel : ViewModel() {

    private val _productState = mutableStateOf(ProductState())
    val productState: State<ProductState> = _productState


    suspend fun make(product: Product) {
        if (product.type == "PC") {
            makePC(product)
            return
        }
        val res: MutableList<MutableList<String>> = mutableListOf()
        if (product.type == "Cooling") {
            val resultSetSockets = DbHelper.getInfoAboutCooling(product)
            resultSetSockets.next()
            val key = resultSetSockets.getString("socket")
            if (!resultSetSockets.wasNull()) {
                val tmp = mutableListOf<String>()
                tmp.add("Сокет(-ы)")
                tmp.add(key)
                res.add(tmp)
            }
        } else if (product.type == "PCCase") {
            val resultSetSockets = DbHelper.getInfoAboutFormFactors(product)
            resultSetSockets.next()
            val key = resultSetSockets.getString("formFactors")
            if (!resultSetSockets.wasNull()) {
                val tmp = mutableListOf<String>()
                tmp.add("Форм-фактор(-ы)")
                tmp.add(key)
                res.add(tmp)
            }
        }
        val resultSetColumnsAndComments = DbHelper.getColumnsAndCommentsInfo(product.type)
        val resultSetValues = DbHelper.getInfo(product)
        if (resultSetValues.isBeforeFirst && resultSetColumnsAndComments.isBeforeFirst) {
            resultSetValues.next()
            while (resultSetColumnsAndComments.next()) {
                val value =
                    resultSetValues.getObject(resultSetColumnsAndComments.getString("COLUMN_NAME"))
                        ?: continue
                val key = resultSetColumnsAndComments.getString("COLUMN_COMMENT")
                if (key == "") continue
                val tmp = mutableListOf<String>()
                tmp.add(key)
                tmp.add(value.toString())
                res.add(tmp)
            }
        }
        resultSetValues.close()
        resultSetColumnsAndComments.close()
        val booleans = findProducts(product)
        _productState.value = _productState.value.copy(
            loading = false,
            keysAndValues = res,
            isInFavourite = booleans.first,
            isInCart = booleans.second
        )
    }

    private suspend fun makePC(product: Product) {
        val resultSetCommentsAndColumns = DbHelper.getColumnsAndCommentsInfo(product.type)

        if (!resultSetCommentsAndColumns.isBeforeFirst) {
            _productState.value = _productState.value.copy(
                loading = false
            )
            return
        }
        val res: MutableList<MutableList<String>> = mutableListOf()
        val resultSetValues = DbHelper.getInfo(product)

        if (resultSetValues.isBeforeFirst && resultSetCommentsAndColumns.isBeforeFirst) {
            resultSetValues.next()
            while (resultSetCommentsAndColumns.next()) {
                val column = resultSetCommentsAndColumns.getString("COLUMN_NAME")
                val key = resultSetCommentsAndColumns.getString("COLUMN_COMMENT")
                if (column == "idPC") continue
                val value = resultSetValues.getInt(column)
                if (resultSetValues.wasNull()) continue
                val resultSetName = DbHelper.getName(value)
                resultSetName.next()
                val name = resultSetName.getString("res")
                resultSetName.close()
                val tmp = mutableListOf<String>()
                tmp.add(key)
                tmp.add(name)
                res.add(tmp)
            }
        }

        resultSetCommentsAndColumns.close()
        resultSetValues.close()
        val booleans = findProducts(product)

        _productState.value = _productState.value.copy(
            loading = false,
            keysAndValues = res,
            isInFavourite = booleans.first,
            isInCart = booleans.second
        )
    }

    private suspend fun findProducts(product: Product): Pair<Boolean, Boolean> {
        if (User.Id.id == -1) return Pair(false, false)
        val resultSetProductInFavourite = DbHelper.getProductInFavourite(product)
        val resultSetProductInCart = DbHelper.getProductInCart(product)
        var productInFavourite = false
        var productInCart = false
        if (resultSetProductInFavourite.isBeforeFirst) {
            productInFavourite = true
        }
        if (resultSetProductInCart.isBeforeFirst) {
            productInCart = true
        }

        resultSetProductInFavourite.close()
        resultSetProductInCart.close()

        return Pair(productInFavourite, productInCart)
    }


    suspend fun addWishList(product: Product): Int {
        try {
            val resultSetWishList = DbHelper.getProductWishList(product)
            if (resultSetWishList.isBeforeFirst) {
                resultSetWishList.close()
                DbHelper.deleteProductWishList(product)
                return 1 // DELETED
            }
            DbHelper.addProductToWishList(product)
            resultSetWishList.close()
            return 0 //ADDED
        } catch (ex: Exception) {
            return -1 //ERROR
        }
    }


    suspend fun addCart(product: Product): Int {
        if (product.inStock <= 0) return 2 // NOT ON SALE
        try {
            val resultSetCart = DbHelper.getProductCart(product)
            if (resultSetCart.isBeforeFirst) {
                resultSetCart.close()
                DbHelper.deleteProductCart(product)
                return 1 // DELETED
            }
            DbHelper.addProductToCart(product)
            resultSetCart.close()
            return 0 //ADDED
        } catch (ex: Exception) {
            return -1 //ERROR
        }
    }


    data class ProductState(
        val loading: Boolean = true,
        val keysAndValues: MutableList<MutableList<String>> = mutableListOf(),
        val isInFavourite: Boolean = false,
        val isInCart: Boolean = false
    )

}



