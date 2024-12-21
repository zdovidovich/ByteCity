package com.example.bytecity.view.MakeOrderPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.model.User
import com.example.bytecity.view.CartPage.CartProductItem
import com.example.bytecity.view.MainComposables.MainSnackbar
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun makeOrderPage(
    navHostController: NavHostController,
    productsForCart: List<ProductForCart>
) {
    val makeOrderViewModel: MakeOrderViewModel = viewModel()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
        snackbarHost = {
            MainSnackbar(snackbarHostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            var name by remember { mutableStateOf("") }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(text = "Оформление заказа", fontSize = 30.sp)
                }
                items(productsForCart) {
                    CartProductItem(
                        productForCart = it,
                        navHostController = navHostController,
                        canBeChanged = false
                    )
                }
                item {
                    Text("Контактная информация", fontSize = 24.sp)
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    TextField(value = name, onValueChange = {
                        name = it
                    }, label = { Text("Имя") }, singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,

                            )
                    )

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    TextField(
                        value = User.Id.email, onValueChange = {}, readOnly = true,
                        label = { Text("Email") }, singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,

                            )
                    )

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    TextField(
                        value = User.Id.contactNumber, onValueChange = {}, readOnly = true,
                        label = { Text("Телефон") }, singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray,

                            )
                    )
                }
                item {
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Способы доставки", fontSize = 24.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = true, onCheckedChange = {},
                            colors = CheckboxDefaults.colors(
                                checkedColor = MainColor.AppColor.value
                            )
                        )
                        Text("Самовывоз", fontSize = 18.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = false, onCheckedChange = {})
                        Text("Доставка(На данный момент не доступно)", fontSize = 18.sp)
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    val price =
                        (productsForCart.fold(0.0) { acc, productForCart -> acc + productForCart.product.price * productForCart.qty } * 100).roundToInt() / 100.0
                    Text("Итого $price BYN", fontSize = 20.sp)
                }
                item {
                    Button(
                        onClick = {
                            val res = makeOrderViewModel.makeOrder(
                                products = productsForCart,
                                name = name,
                                context = context
                            )

                            when (res) {
                                2 -> {
                                    scope.launch {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            message = "Вы ввели пустое имя"
                                        )
                                    }
                                }

                                1 -> {
                                    scope.launch {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            message = "Ошибка. Повторите позже"
                                        )
                                    }
                                }

                                else -> {
                                    navHostController.navigate(Screens.MainContentScreens.route) {
                                        popUpTo(Screens.MainContentScreens.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }


                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainColor.AppColor.value,
                            contentColor = Color.White
                        ), shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Оформить заказ")
                    }
                }
            }
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun makeOrderPagePreview() {
//    val navHostController = rememberNavController()
//    val str = "https://img.sila.by/catalog/img13/tovar138536.jpg"
//
//    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//    val rdate = formatter.parse("2021-03-30")
//    val product: Product = Product(
//        99,
//        "Asus",
//        "ROG Strix Z590-E",
//        "PC",
//        539.99,
//        str,
//        10,
//        rdate!!
//    )
//    val readProduct = ProductForCart(product, 100)
//    val l: List<ProductForCart> = listOf(readProduct)
//    makeOrderPage(navHostController, l)
//}