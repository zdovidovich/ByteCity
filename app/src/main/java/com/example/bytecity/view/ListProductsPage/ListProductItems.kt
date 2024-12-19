package com.example.bytecity.view.ListProductsPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.MainColor
import com.example.bytecity.view.MainComposables.TopBar
import com.example.bytecity.viewmodel.ListProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ListProductPreScreen(
    type: String,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController
) {
    val listProductViewModel: ListProductViewModel = viewModel()
    listProductViewModel.loadProducts(type)
    val viewState by listProductViewModel.listProductState
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading ->
                CircularProgressIndicator(modifier.align(Alignment.Center))

            viewState.error != "" ->
                Text("Произошла ошибка, повторите попытку")

            else -> {
                ListProductPage(viewState.products, scope, drawerState, navHostController)
            }
        }
    }
}


@Composable
fun ListProductPage(products: List<Product>, scope: CoroutineScope, drawerState: DrawerState, navHostController: NavHostController) {
    val snackbarHostState = remember{ SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        topBar = {
            TopBar {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Назад"
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MainColor.AppColor.value,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(8.dp),
                )

            }
        }) {
        Spacer(modifier = Modifier.padding(it))
        LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier
            .fillMaxSize()
            .padding(it))
        {
            items(products)
            { product ->
                ProductItemPreScreen(product = product, navHostController=navHostController, snackbarHostState = snackbarHostState)
            }
        }
        Spacer(modifier = Modifier.padding(it))
    }
}
