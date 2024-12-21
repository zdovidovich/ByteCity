package com.example.bytecity.view.FavouritePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.view.ListProductsPage.ProductItemPreScreen
import com.example.bytecity.view.MainComposables.MainSnackbar
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FavouritePreScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController,
    idProductToDelete: Product?
) {
    val favouriteViewModel: FavouriteViewModel = viewModel()
    val viewState by favouriteViewModel.favouriteState

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text(viewState.error!!)
            }

            else -> {
                if(idProductToDelete != null){
                    favouriteViewModel.deleteProduct(product = idProductToDelete)
                }
                FavouritePage(
                    products = viewState.products,
                    scope = scope,
                    drawerState = drawerState,
                    navHostController = navHostController,
                    favouriteViewModel = favouriteViewModel
                )
            }
        }
    }
}


@Composable
fun FavouritePage(
    products: List<Product>,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController,
    favouriteViewModel: FavouriteViewModel
) {


    val snackbarHostState = remember {
        SnackbarHostState()
    }


    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            })
        },
        snackbarHost = {
            MainSnackbar(snackbarHostState = snackbarHostState)
        }
    ) {
        Column(modifier=Modifier.fillMaxSize().padding(it)) {
            Text("Избранное", fontSize = 30.sp)
            if (products.isEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Здесь пусто :)", modifier = Modifier.padding(it), fontSize = 26.sp)
                }
            } else {
                LazyVerticalGrid(
                    GridCells.Fixed(2), modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(products) { product ->
                        ProductItemPreScreen(
                            product = product,
                            navHostController = navHostController,
                            snackbarHostState = snackbarHostState
                        ) {
                            favouriteViewModel.deleteProduct(product)
                        }
                    }
                }
            }
        }
    }
}