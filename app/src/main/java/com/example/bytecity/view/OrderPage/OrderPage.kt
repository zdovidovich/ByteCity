package com.example.bytecity.view.OrderPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.view.CartPage.CartProductItem
import com.example.bytecity.view.MainComposables.MainSnackbar
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OrderPage(
    order: Order,
    navHostController: NavHostController,
    scope:CoroutineScope
) {
    val snackbarHostState = remember{ SnackbarHostState() }
    val orderViewModel:OrderViewModel = viewModel()

    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                }
            })
        },
        snackbarHost = {MainSnackbar(snackbarHostState = snackbarHostState)}
    ) {
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(8.dp)) {
            Text(
                text = "Дата заказа: ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = order.registrationDate.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Статус: ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(text = order.status, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Список товаров: ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
            ) {
                items(order.products) { product ->
                    CartProductItem(
                        productForCart = product,
                        navHostController = navHostController,
                        canBeChanged = false,
                        buttonMakeReview = {
                            Button(onClick = {
                                scope.launch {
                                    when(orderViewModel.checkReview(product.product.idProduct)){
                                        200 ->{
                                            navHostController.currentBackStackEntry?.savedStateHandle?.set("productformakereview", product.product.idProduct)
                                            navHostController.navigate(Screens.MakeReviewPage.route)
                                        }
                                        1 ->{
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Вы уже оставляли отзыв на этот товар"
                                            )
                                        }
                                        else ->{
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Ошибка, повторите позже"
                                            )
                                        }
                                    }


                                }
                            }, shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value)
                                ) {
                                    Text("Оставить отзыв")
                            }
                        }
                    )
                }
            }
        }
    }
}