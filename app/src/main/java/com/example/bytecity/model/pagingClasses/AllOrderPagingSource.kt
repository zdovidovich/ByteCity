package com.example.bytecity.model.pagingClasses

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.model.DbConnection
import com.example.bytecity.model.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class AllOrderPagingSource(private val context: Context) : PagingSource<Int, Order>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        val orders: MutableList<Order> = mutableListOf()
        var resultSetOrdersId: ResultSet? = null
        var connection: DbConnection? = null
        return try {
            withContext(Dispatchers.IO) {
                connection = DbConnection()
                connection?.connect(context)
            }
            resultSetOrdersId = DbHelper.getOrdersId(pageSize, page * pageSize, connection!!)
            while (resultSetOrdersId.next()) {
                orders.add(Order.parse(resultSetOrdersId, connection!!))
            }
            LoadResult.Page(
                data = orders,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (orders.isEmpty()) null else page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex) // smth's bad
        } finally {
            withContext(Dispatchers.IO) {
                connection?.connection?.close()
            }
            resultSetOrdersId?.close()
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Order>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}