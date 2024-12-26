package com.example.bytecity.view.SearchPage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.pagingClasses.SearchProductsPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchPageViewModel : ViewModel() {
    private val _pager = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val pager: StateFlow<PagingData<Product>> = _pager

    fun getProducts(text: String, context: Context) {
        val flow = Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { SearchProductsPagingSource(text, context) }).flow
        viewModelScope.launch {
            flow.collectLatest {
                _pager.value = it
            }
        }
    }

}