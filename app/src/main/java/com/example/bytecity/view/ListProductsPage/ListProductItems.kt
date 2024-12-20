package com.example.bytecity.view.ListProductsPage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bytecity.model.MainColor
import com.example.bytecity.view.MainComposables.TopBar
import com.example.bytecity.viewmodel.ListProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


//@Composable
//fun ListProductPreScreen(
//    type: String,
//    modifier: Modifier = Modifier,
//    scope: CoroutineScope,
//    drawerState: DrawerState,
//    navHostController: NavHostController
//) {
//    val listProductViewModel: ListProductViewModel = viewModel()
//    listProductViewModel.loadProducts(type)
//    val viewState by listProductViewModel.listProductState
//    Box(modifier = Modifier.fillMaxSize()) {
//        when {
//            viewState.loading ->
//                CircularProgressIndicator(modifier.align(Alignment.Center))
//
//            viewState.error != "" ->
//                Text("Произошла ошибка, повторите попытку")
//
//            else -> {
//                ListProductPage(viewState.products, scope, drawerState, navHostController)
//            }
//        }
//    }
//}


@Composable
fun ListProductPage(
    type: String,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController
) {

    val viewModel: ListProductViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(type) {
        viewModel.findProducts(type, context)
    }
    val products = viewModel.pager.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    
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
        when (products.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier=Modifier.padding(it))
            }
            is LoadState.Error -> {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Ошибка, повторите позже"
                    )
                }
            }

            else -> {
                LazyVerticalGrid(
                    GridCells.Fixed(2), modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )
                {
                    items(count = products.itemCount)
                    { index ->
                        ProductItemPreScreen(
                            product = products[index]!!,
                            navHostController = navHostController,
                            snackbarHostState = snackbarHostState
                        )

                    }
                }
            }
        }
    }
}
