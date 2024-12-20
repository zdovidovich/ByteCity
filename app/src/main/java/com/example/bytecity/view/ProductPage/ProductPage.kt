package com.example.bytecity.view.ProductPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bytecity.R
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.model.User
import com.example.bytecity.view.MainComposables.TopBar
import com.example.bytecity.viewmodel.ProductPageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ProductPreScreen(
    product: Product,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val productPageViewModel: ProductPageViewModel = viewModel()
    productPageViewModel.make(product)
    val viewState by productPageViewModel.productState
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            else -> {
                ProductPage(product, viewState, productPageViewModel, navHostController)
            }
        }
    }

}

@Composable
fun ProductPage(
    product: Product,
    viewState: ProductPageViewModel.ProductState,
    productPageViewModel: ProductPageViewModel,
    navHostController: NavHostController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var favouriteString by remember { mutableStateOf(if (viewState.isInFavourite) "В Избранном" else "В Избранное") }
    var cartString by remember { mutableStateOf(if (viewState.isInCart) "В Корзине" else "В Корзину") }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        topBar = {
            TopBar {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
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
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
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
                                when (productPageViewModel.addWishList(product)) {
                                    1 -> {
                                        favouriteString = "В избранное"
                                        navHostController.previousBackStackEntry?.savedStateHandle?.set("producttofavourite", product)
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            "Удалено из избранного",
                                            duration = SnackbarDuration.Short
                                        )
                                    }

                                    0 -> {
                                        favouriteString = "В избранном"
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

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value)
                ) { Text(favouriteString) }
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    onClick =
                    {
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
                                when (productPageViewModel.addCart(product)) {
                                    2 -> {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            message = "Не в продаже"
                                        )
                                    }

                                    1 -> {
                                        cartString = "В корзину"
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            "Удалено из корзины",
                                            duration = SnackbarDuration.Short
                                        )
                                    }

                                    0 -> {
                                        cartString = "В корзине"
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        val res = snackbarHostState.showSnackbar(
                                            "Добавлено",
                                            actionLabel = "В корзину",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (res == SnackbarResult.ActionPerformed) {
                                            withContext(Dispatchers.Main) {
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
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value)
                ) {
                    Text(
                        cartString
                    )
                }
            }
        }) {
        Spacer(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 2.dp)
        ) {
            item {
                Column(horizontalAlignment = Alignment.Start) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(product.imageProduct)
                                .crossfade(true).error(
                                    R.drawable.error_image
                                ).build()
                        ),
                        contentDescription = "${product.brand} ${product.model}",
                        modifier = Modifier
                            .wrapContentSize()
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Text("${product.brand} ${product.model}", fontSize = 30.sp)
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    var price = "${product.price} BYN"
                    if (product.inStock == 0) {
                        price = "Не в продаже"
                    }
                    Text(price, fontSize = 32.sp)
                }
            }
            item {
                ProductAttribute(
                    key = "Дата выхода",
                    value = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.ENGLISH
                    ).format(product.releaseDate)
                )
            }
            items(viewState.keysAndValues) { row ->
                ProductAttribute(key = row[0], value = row[1])
            }
        }
    }

}

//
//@Preview(showBackground = true)
//@Composable
//fun ProductPagePreview() {
//    val str =
//        "https://256bit.by/upload/resize_cache/iblock/e9b/450_450_140cd750bba9870f18aada2478b24840a/o2uw77dvg8gm10blc9p0nvjcmg8a7ig2.jpeg"
//    val product: Product = Product(
//        1,
//        "Intel",
//        "Core i9-11900K",
//        "Processor",
//        539.99,
//        str,
//        10,
//        Date(2222L)
//    )
////    ProductPreScreen(product = product)
//
//}