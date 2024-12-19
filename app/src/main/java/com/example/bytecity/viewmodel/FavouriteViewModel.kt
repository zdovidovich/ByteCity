package com.example.bytecity.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.Db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class FavouriteViewModel : ViewModel() {

    private val _favouriteState = mutableStateOf(FavouriteState())
    val favouriteState:State<FavouriteState> = _favouriteState

    init{
        getFavouriteProducts()
    }


    private fun getFavouriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
            val resultSetProductIds = Db.getAllProductWishList()
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
                val resultSetProduct = Db.getProductById(id)
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
                val product: Product = Product(
                    idProduct = id,
                    brand = resultSetProduct.getString("brand"),
                    model = resultSetProduct.getString("model"),
                    type = resultSetProduct.getString("type"),
                    price =  String.format("%.2f", resultSetProduct.getDouble("price") * (1 - discountValue)).replace(",", ".").toDouble(),
                    imageProduct = resultSetProduct.getString("imageProduct"),
                    inStock = resultSetProduct.getInt("inStock"),
                    releaseDate = rdate!!
                )
                productsInWishList.add(product)
                resultSetProduct.close()
            }
            resultSetProductIds.close()
            withContext(Dispatchers.Main){
                _favouriteState.value = _favouriteState.value.copy(
                    loading = false,
                    products = productsInWishList
                )
            }
        }
            catch (e:Exception){
                _favouriteState.value = _favouriteState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }

    }


    data class FavouriteState(
        var loading: Boolean = true,
        var error: String? = null,
        var products: MutableList<Product> = mutableListOf()
    )
}