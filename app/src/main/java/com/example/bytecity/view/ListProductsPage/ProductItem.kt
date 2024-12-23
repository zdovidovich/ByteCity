package com.example.bytecity.view.ListProductsPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bytecity.R
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.Screens
import com.example.bytecity.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProductItemPreScreen(
    product: Product,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDeleteFavorite: () -> Unit = {}
) {
    val productItemViewModel: ProductItemViewModel = viewModel(
        key = product.idProduct.toString()
    )
    productItemViewModel.getRating(product)
    val viewState by productItemViewModel.productItemState


    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            !viewState.error.isNullOrEmpty() -> {
                Text("Произошла ошибка, повторите попытку")
            }

            else -> {
                ProductItem(
                    product = product,
                    productItemViewModel = productItemViewModel,
                    rating = viewState.rating,
                    navHostController = navHostController,
                    snackbarHostState = snackbarHostState,
                    onDeleteFavorite = onDeleteFavorite
                )
            }
        }
    }
}


@Composable
fun ProductItem(
    product: Product,
    productItemViewModel: ProductItemViewModel,
    rating: Float,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    onDeleteFavorite: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .height(320.dp)
        , verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(product.imageProduct)
                    .crossfade(true).error(
                        R.drawable.error_image
                    ).build()
            ),
            contentDescription = product.brand,
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        "product",
                        product
                    )
                    navHostController.navigate(Screens.ProductPageScreens.route)
                }
        )
        Text(
            text = "${product.brand} ${product.model}",
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp, start = 8.dp)
        )
        Row {
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
            Text(
                text = "$rating",
                color = Color.Yellow,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 4.dp, start = 8.dp).clickable {
                    navHostController.currentBackStackEntry?.savedStateHandle?.set("productsforreview", product.idProduct)
                    navHostController.navigate(Screens.ReviewPage.route)
                }
            )
        }
        var price = "${product.price} BYN"
        if (product.inStock <= 0)
            price = "Не в продаже"

        Text(
            text = price,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 8.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(onClick =
            {
                if (User.Id.id == -1) {
                    scope.launch(Dispatchers.Main) {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            "Вы не вошли в аккаунт",
                            duration = SnackbarDuration.Short
                        )

                    }
                } else {
                    scope.launch(Dispatchers.IO) {
                        when (productItemViewModel.addWishList(product)) {
                            1 -> {
                                launch {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                    snackbarHostState.showSnackbar(
                                        "Удалено из избранного",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                launch {
                                    onDeleteFavorite()
                                }
                            }

                            0 -> {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                val res = snackbarHostState.showSnackbar(
                                    "Добавлено",
                                    actionLabel = "В избранное",
                                    duration = SnackbarDuration.Short
                                )
                                if (res == SnackbarResult.ActionPerformed) {
                                    withContext(Dispatchers.Main) {
                                        navHostController.navigate(Screens.FavouriteProductScreens.route)
                                    }
                                }
                            }

                            else -> {
                                snackbarHostState.showSnackbar(
                                    "Не получилось, повторите попытку",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }
            }) {
                Icon(Icons.Outlined.Star, "В Избранное")
            }
            IconButton(onClick = {
                if (User.Id.id == -1) {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            "Вы не вошли в аккаунт",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    scope.launch(Dispatchers.IO) {
                        when (productItemViewModel.addCart(product)) {
                            2 -> {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    message = "Не в продаже"
                                )
                            }
                            1 -> {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    "Удалено из корзины",
                                    duration = SnackbarDuration.Short
                                )
                            }

                            0 -> {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                val res = snackbarHostState.showSnackbar(
                                    "Добавлено",
                                    actionLabel = "В корзину",
                                    duration = SnackbarDuration.Short
                                )
                                if(res == SnackbarResult.ActionPerformed){
                                    withContext(Dispatchers.Main){
                                        navHostController.navigate(Screens.CartProductScreens.route)
                                    }
                                }
                            }
                            else -> {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    "Не получилось, повторите попытку",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }
            }) {
                Icon(Icons.Outlined.ShoppingCart, "В Корзину")
            }

        }
    }
}

