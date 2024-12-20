package com.example.bytecity.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.model.pagingClasses.AllOrderPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllOrderPageViewModel : ViewModel() {

    private val _pager = MutableStateFlow<PagingData<Order>>(PagingData.empty())
    val pager: StateFlow<PagingData<Order>> = _pager

    fun findProducts(context: Context){
        val flow = Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = { AllOrderPagingSource(context) }).flow
        viewModelScope.launch {
            flow.collectLatest{
                _pager.value = it
            }
        }

    }

}