package com.example.bytecity.model.pagingClasses

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.DbConnection
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class SearchProductsPagingSource(val text: String, val context: Context) :
    PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        var connection: DbConnection? = null
        return try {
            connection = DbConnection()
            withContext(Dispatchers.IO) {
                connection.connect(context)
            }
            val resultSetListProduct: ResultSet =
                DbHelper.getProductsBySearching(text, connection, pageSize, page * pageSize)

            val allProducts = mutableListOf<Product>()
            while (resultSetListProduct.next()) {
                val product = Product.parse(resultSetListProduct)
                allProducts.add(product)
            }
            resultSetListProduct.close()
            LoadResult.Page(
                data = allProducts,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (allProducts.isEmpty()) null else page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)

        } finally {
            connection?.connection?.close()
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}