package com.example.bytecity.view.CartPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.view.MainComposables.MainSnackbar
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CartPreScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController,

    ) {
    val cartViewModel: CartViewModel = viewModel()
    cartViewModel.viewModelScope.launch {
        cartViewModel.getProductsFromCart()
    }
    val viewState by cartViewModel.cartState
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text(viewState.error!!)
            }

            else -> {
                CartPage(
                    cartViewModel = cartViewModel,
                    products = viewState.products,
                    scope = scope,
                    drawerState = drawerState,
                    navHostController = navHostController
                )
            }
        }
    }
}


@Composable
fun CartPage(
    cartViewModel: CartViewModel,
    products: List<ProductForCart>,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController
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
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value),
                    onClick = {
                        cartViewModel.viewModelScope.launch {
                            cartViewModel.cleanCart()
                        }
                    }) {
                    Text("Очистить корзину")
                }
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value),
                    onClick = {
                        if (products.isNotEmpty()) {
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "productsforcart",
                                products
                            )
                            navHostController.navigate(Screens.MakeOrderPage.route)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Нечего заказывать :)"
                                )
                            }
                        }
                    }) {
                    Text("Заказать")
                }

            }
        },
        snackbarHost = {
            MainSnackbar(snackbarHostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text("Корзина", fontSize = 30.sp)
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(products) { product ->
                        CartProductItem(
                            productForCart = product,
                            navHostController = navHostController,
                            snackbarHostState = snackbarHostState,
                            onPlusOneProduct = {
                                cartViewModel.plusProduct(product = product)
                            },
                            onMinusOneProduct = {
                                cartViewModel.minusProduct(product = product)
                            },
                            onDeleteProduct = {
                                cartViewModel.deleteProduct(product = product)
                                scope.launch {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        message = "Удалено",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}