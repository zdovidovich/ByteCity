package com.example.bytecity.view.CartPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bytecity.R
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Screens
import kotlinx.coroutines.launch

@Composable
fun CartProductItem(
    productForCart: ProductForCart,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    canBeChanged: Boolean = true,
    onPlusOneProduct: () -> Unit = {},
    onMinusOneProduct: () -> Unit = {},
    onDeleteProduct: () -> Unit = {},
    buttonMakeReview: @Composable () -> Unit = {}
) {
    val cartItemViewModel: CartItemViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val size = 162.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .size(size),
        verticalAlignment = Alignment.Top

    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(productForCart.product.imageProduct)
                    .crossfade(true).error(
                        R.drawable.error_image
                    ).build()
            ),
            contentDescription = "${productForCart.product.brand} ${productForCart.product.model}",
            modifier = Modifier
                .wrapContentSize()
                .size(128.dp)
                .aspectRatio(1f)
                .clickable {
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        "product",
                        productForCart.product
                    )
                    navHostController.navigate(Screens.ProductPageScreens.route)
                }
        )
        Column(modifier = Modifier.padding(4.dp)) {
            Text(productForCart.product.brand)
            Text(productForCart.product.model)
            Text(
                String.format("%.2f BYN", productForCart.product.price * productForCart.qty)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(64.dp))
                    .background(Color.White),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (canBeChanged) {
                    IconButton(onClick = {
                        scope.launch {
                            val res = cartItemViewModel.addOneProduct(
                                productForCart = productForCart,
                                frontAddOneProduct = onPlusOneProduct
                            )
                            when (res) {
                                1 -> {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        "Достигнут максимум",
                                        duration = SnackbarDuration.Short
                                    )
                                }

                                200 -> {}
                                else -> {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        "Ошибка, повторите позже",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }

                    }) {
                        Icon(Icons.Filled.KeyboardArrowUp, "Больше")
                    }
                    Text("${productForCart.qty}")
                    IconButton(onClick = {
                        scope.launch {
                            val res = cartItemViewModel.removeOneProduct(
                                productForCart = productForCart,
                                frontRemoveOneProduct = onMinusOneProduct
                            )
                            when (res) {
                                1 -> {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        "Меньше 1 нельзя :)",
                                        duration = SnackbarDuration.Short
                                    )
                                }

                                200 -> {}
                                else -> {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        "Ошибка, повторите позже",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    }) {
                        Icon(Icons.Filled.KeyboardArrowDown, "Меньше")
                    }
                } else {
                    Text("Количество: ${productForCart.qty}")
                }
            }

            buttonMakeReview()
        }

        if (canBeChanged) {
            IconButton(onClick = {
                scope.launch {
                    cartItemViewModel.removeProductFromCart(
                        productForCart = productForCart,
                        frontRemoveProductFromCart = onDeleteProduct
                    )
                }
            }) {
                Icon(Icons.Filled.Delete, "Удалить")
            }
        }

    }
}