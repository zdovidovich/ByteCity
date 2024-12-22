package com.example.bytecity.model.pagingClasses

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Db
import com.example.bytecity.model.DbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.Locale

class AllOrderPagingSource(private val context: Context): PagingSource<Int, Order>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        val orders: MutableList<Order> = mutableListOf()
        var resultSetOrdersId: ResultSet? = null
        var resultSetOrderDetails: List<ResultSet?> = listOf()
        var resultSetOrderProducts: List<ResultSet?> = listOf()

        return try {
            withContext(Dispatchers.IO) {
                val connection = DbConnection()
                connection.connect(context)
                resultSetOrdersId = Db.getOrdersId(pageSize, page * pageSize, connection)
                resultSetOrderDetails = Db.getOrderDetails(resultSetOrdersId!!, pageSize, page * pageSize, connection)
                resultSetOrdersId?.close()
                resultSetOrdersId = Db.getOrdersId(pageSize, page * pageSize, connection)
                resultSetOrderProducts = Db.getOrderProducts(resultSetOrdersId!!, pageSize, page * pageSize, connection)
                for (i in resultSetOrderDetails.indices) {
                    resultSetOrderDetails[i]?.next()
                    val name = resultSetOrderDetails[i]?.getString("name")
                    val status = resultSetOrderDetails[i]?.getString("status")
                    val registrationDate = resultSetOrderDetails[i]?.getDate("registrationDate")
                    val productsTemp = mutableListOf<ProductForCart>()
                    while(resultSetOrderProducts[i]?.next()!!){
                        val resultSetProduct = Db.getProductById(resultSetOrderProducts[i]?.getInt("idProduct")!!, pageSize, page * pageSize, connection)
                        resultSetProduct.next()

                        val idDiscount = resultSetProduct.getInt("idDiscount")
                        var discountValue = 0.0
                        if(!resultSetProduct.wasNull()){
                            val resultSetDiscount = Db.getInfoDiscount(idDiscount, pageSize, page * pageSize, connection)
                            resultSetDiscount.next()
                            discountValue = resultSetDiscount.getDouble("value")
                            resultSetDiscount.close()
                        }
                        val product = Product.parse(resultSetProduct, discountValue = discountValue)

                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                        val rdate = formatter.parse(resultSetProduct.getDate("releaseDate").toString())
                        val qty = resultSetOrderProducts[i]?.getInt("quantity")!!
                        productsTemp.add(ProductForCart(product, qty))
                        resultSetProduct.close()
                    }
                    orders.add(Order(name=name!!, products= productsTemp, status = status!!, registrationDate = registrationDate!!))
                }

            }
            LoadResult.Page(
                data = orders,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (orders.isEmpty()) null else page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex) // smth's bad
        } finally {
            resultSetOrdersId?.close()
            for (i in resultSetOrderDetails) {
                i?.close()
            }
            for (i in resultSetOrderProducts) {
                i?.close()
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Order>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}