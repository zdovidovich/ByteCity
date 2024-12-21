package com.example.bytecity.view.ListProductsPage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.pagingClasses.ProductPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.bytecity.businessClasses.Product
//import com.example.bytecity.model.Db
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.text.SimpleDateFormat
//import java.util.Locale
//
//
//class ListProductViewModel : ViewModel() {
//
//    private val _listProductState = mutableStateOf(ListProductState())
//    val listProductState: State<ListProductState> = _listProductState
//
//
//    fun loadProducts(type: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val resultSetListProduct = Db.getProducts(type)
//            if (!resultSetListProduct.isBeforeFirst) {
//                withContext(Dispatchers.Main) {
//                    _listProductState.value = ListProductState(
//                        loading = false,
//                        error = "ERROR"
//                    )
//                }
//                return@launch
//            }
//            val allProducts = mutableListOf<Product>()
//            while (resultSetListProduct.next()) {
//
//                val idDiscount = resultSetListProduct.getInt("idDiscount")
//                var discountValue = 0.0
//                if(!resultSetListProduct.wasNull()){
//                    val resultSetDiscount = Db.getInfoDiscount(idDiscount)
//                    resultSetDiscount.next()
//                    discountValue = resultSetDiscount.getDouble("value")
//                    resultSetDiscount.close()
//                }
//
//
//                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//                val rdate = formatter.parse(resultSetListProduct.getDate("releaseDate").toString())
//                val product = Product(
//                    idProduct = resultSetListProduct.getInt("idProduct"),
//                    brand = resultSetListProduct.getString("brand"),
//                    model = resultSetListProduct.getString("model"),
//                    type = resultSetListProduct.getString("type"),
//                    price =  String.format("%.2f", resultSetListProduct.getDouble("price") * (1 - discountValue)).replace(",", ".").toDouble(),
//                    imageProduct = resultSetListProduct.getString("imageProduct"),
//                    inStock = resultSetListProduct.getInt("inStock"),
//                    releaseDate = rdate!!
//                )
//                allProducts.add(product)
//            }
//
//            resultSetListProduct.close()
//            withContext(Dispatchers.Main) {
//                _listProductState.value = ListProductState(
//                    loading = false,
//                    products = allProducts
//                )
//            }
//
//        }
//    }
//
//
//    data class ListProductState(
//        var loading: Boolean = true,
//        val products: MutableList<Product> = mutableListOf(),
//        var error: String = ""
//    )
//}


class ListProductViewModel : ViewModel() {
    private val _pager = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val pager: StateFlow<PagingData<Product>> = _pager



    fun findProducts(type:String, context: Context){
        val flow = Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { ProductPagingSource(type, context) }).flow
        viewModelScope.launch {
            flow.collectLatest{
                _pager.value = it
            }
        }

    }
}
