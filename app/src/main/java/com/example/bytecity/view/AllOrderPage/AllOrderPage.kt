package com.example.bytecity.view.AllOrderPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.view.MainComposables.TopBar
import com.example.bytecity.viewmodel.AllOrderPageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AllOrderPreScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    val allOrderPageViewModel: AllOrderPageViewModel = viewModel()
    val viewState by allOrderPageViewModel.orderState
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            !viewState.error.isNullOrEmpty() -> {
                Text(viewState.error!!, fontSize = 20.sp)
            }

            else -> {
                AllOrderPage(viewState.orders, drawerState, scope, navHostController)
            }
        }
    }

}


@Composable
fun AllOrderPage(
    orders: List<Order>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {

    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }
                }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            })
        }
    ) {
        Text("Заказы", fontSize = 26.sp)
        LazyColumn(
            modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(orders) { order ->
                AllOrderItem(order = order, navHostController = navHostController)
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun OrderPagePreview() {
//    OrderPreScreen()
//}