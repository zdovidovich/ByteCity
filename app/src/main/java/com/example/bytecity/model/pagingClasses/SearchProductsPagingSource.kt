package com.example.bytecity.model.pagingClasses

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.Db
import com.example.bytecity.model.DbConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class SearchProductsPagingSource(val text:String, val context:Context): PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        return try {
            val connection = DbConnection()
            withContext(Dispatchers.IO) {
                connection.connect(context)
            }
            val resultSetListProduct: ResultSet
            withContext(Dispatchers.IO) {
                resultSetListProduct =
                    Db.getProductsBySearching(text, connection, pageSize, page * pageSize)
            }
            val allProducts = mutableListOf<Product>()
            while (resultSetListProduct.next()) {

                val idDiscount = resultSetListProduct.getInt("idDiscount")
                var discountValue = 0.0
                if (!resultSetListProduct.wasNull()) {
                    val resultSetDiscount = Db.getInfoDiscount(idDiscount)
                    resultSetDiscount.next()
                    discountValue = resultSetDiscount.getDouble("value")
                    resultSetDiscount.close()
                }

                val product = Product.parse(resultSetListProduct, discountValue)
                allProducts.add(product)
            }
            resultSetListProduct.close()
            connection.connection.close()
            LoadResult.Page(
                data = allProducts,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (allProducts.isEmpty()) null else page + 1
            )
        }
        catch (ex:Exception){
            LoadResult.Error(ex)

        }

    }


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}