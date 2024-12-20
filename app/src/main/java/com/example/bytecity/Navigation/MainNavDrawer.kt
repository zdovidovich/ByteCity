package com.example.bytecity.Navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.model.Category
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.model.User
import kotlinx.coroutines.launch

//@Composable
//fun MainNavDrawer(content: @Composable (CoroutineScope, DrawerState) -> Unit) {
//
//
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//
//    val ItemColors = NavigationDrawerItemDefaults.colors(
//        unselectedContainerColor = Color.White,
//        unselectedTextColor = Color.Black,
//        unselectedIconColor = Color.Black,
//        selectedBadgeColor = Color.LightGray,
//        selectedContainerColor = Color.LightGray,
//        selectedIconColor = Color.Black,
//        selectedTextColor = Color.Black
//    )
//
//    ModalNavigationDrawer(
//        drawerState = drawerState, content = { content(scope, drawerState) },
//        drawerContent = {
//            ModalDrawerSheet(
//                drawerContainerColor = Color.White,
//                drawerContentColor = Color.White,
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                ) {
//
//
//                    NavigationDrawerItem(
//                        shape = RectangleShape,
//                        label = {
//                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
//                                Text(
//                                    "Мой аккаунт"
//                                )
//                            }
//
//                        },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = {
//                            Icon(
//                                Icons.Filled.AccountCircle,
//                                "Мой Аккаунт",
//                                modifier = Modifier.padding(start = 8.dp)
//                            )
//                        },
//                        colors = NavigationDrawerItemDefaults.colors(
//                            unselectedContainerColor = MainColor.AppColor.value,
//                            unselectedTextColor = Color.White,
//                            unselectedIconColor = Color.White,
//                        )
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Главная", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Home, "Главная") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Каталог", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Info, "Каталог") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Избранное", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Default.Star, "Избранное") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Моя корзина", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.ShoppingCart, "Моя корзина") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Мои заказы", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Done, "Мои заказы") },
//                        colors = ItemColors
//                    )
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(8.dp), verticalArrangement = Arrangement.Bottom
//                ) {
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("О нас", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Info, "О нас") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Настройки", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Settings, "Настройки") },
//                        colors = ItemColors
//                    )
//                }
//            }
//
//        },
//        scrimColor = Color.LightGray
//    )
//}
//
//
//@Composable
//fun MainNavDrawer(
//    type: String,
//    content: @Composable (String, CoroutineScope, DrawerState) -> Unit
//) {
//
//
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//
//    val ItemColors = NavigationDrawerItemDefaults.colors(
//        unselectedContainerColor = Color.White,
//        unselectedTextColor = Color.Black,
//        unselectedIconColor = Color.Black,
//        selectedBadgeColor = Color.LightGray,
//        selectedContainerColor = Color.LightGray,
//        selectedIconColor = Color.Black,
//        selectedTextColor = Color.Black
//    )
//
//    ModalNavigationDrawer(
//        drawerState = drawerState, content = { content(type, scope, drawerState) },
//        drawerContent = {
//            ModalDrawerSheet(
//                drawerContainerColor = Color.White,
//                drawerContentColor = Color.White,
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                ) {
//
//
//                    NavigationDrawerItem(
//                        shape = RectangleShape,
//                        label = {
//                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
//                                Text(
//                                    "Мой аккаунт"
//                                )
//                            }
//
//                        },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = {
//                            Icon(
//                                Icons.Filled.AccountCircle,
//                                "Мой Аккаунт",
//                                modifier = Modifier.padding(start = 8.dp)
//                            )
//                        },
//                        colors = NavigationDrawerItemDefaults.colors(
//                            unselectedContainerColor = MainColor.AppColor.value,
//                            unselectedTextColor = Color.White,
//                            unselectedIconColor = Color.White,
//                        )
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Главная", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Home, "Главная") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Каталог", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Info, "Каталог") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Избранное", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Default.Star, "Избранное") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Моя корзина", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.ShoppingCart, "Моя корзина") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Мои заказы", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Done, "Мои заказы") },
//                        colors = ItemColors
//                    )
//                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(8.dp), verticalArrangement = Arrangement.Bottom
//                ) {
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("О нас", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Info, "О нас") },
//                        colors = ItemColors
//                    )
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("Настройки", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                        icon = { Icon(Icons.Filled.Settings, "Настройки") },
//                        colors = ItemColors
//                    )
//                }
//            }
//
//        },
//        scrimColor = Color.LightGray
//    )
//}
//

@Composable
fun MainNavDrawer(
    drawerState: DrawerState,
    navController: NavHostController,
    content: @Composable (navController: NavHostController, drawerState: DrawerState) -> Unit
) {

    val scope = rememberCoroutineScope()


    val ItemColors = NavigationDrawerItemDefaults.colors(
        unselectedContainerColor = Color.White,
        unselectedTextColor = Color.Black,
        unselectedIconColor = Color.Black,
        selectedBadgeColor = Color.LightGray,
        selectedContainerColor = Color.LightGray,
        selectedIconColor = Color.Black,
        selectedTextColor = Color.Black
    )

    ModalNavigationDrawer(
        drawerState = drawerState, content = {
            content(navController, drawerState)
        },
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerContentColor = Color.White,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    NavigationDrawerItem(
                        shape = RectangleShape,
                        label = {
                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    if (User.Id.id == -1) "Мой аккаунт" else "Мой аккаунт"
                                )
                            }
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            if (User.Id.id == -1) {
                                navController.navigate(Screens.LoginScreens.route)
                            } else {
                                navController.navigate(Screens.AccountPage.route) {
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Filled.AccountCircle,
                                "Мой Аккаунт",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = MainColor.AppColor.value,
                            unselectedTextColor = Color.White,
                            unselectedIconColor = Color.White,
                        )
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Главная", fontSize = 20.sp) },
                        selected = false,
                        onClick = {
                            navController.navigate(Screens.MainContentScreens.route) {
                                launchSingleTop = true
                                popUpTo(Screens.MainContentScreens.route){
                                    inclusive = true
                                }
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = { Icon(Icons.Filled.Home, "Главная") },
                        colors = ItemColors
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Каталог", fontSize = 20.sp) },
                        selected = false,
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "cat",
                                Category.MainList.categories
                            )
                            navController.navigate(Screens.CategoryScreens.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = { Icon(Icons.Filled.Info, "Каталог") },
                        colors = ItemColors
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Избранное", fontSize = 20.sp) },
                        selected = false,
                        onClick = {
                            if (User.Id.id == -1) {
                                navController.navigate(Screens.LoginScreens.route)
                            } else {
                                navController.navigate(Screens.FavouriteProductScreens.route) {

                                    launchSingleTop = true
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Star, "Избранное") },
                        colors = ItemColors
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Моя корзина", fontSize = 20.sp) },
                        selected = false,
                        onClick = {
                            if (User.Id.id == -1) {
                                navController.navigate(Screens.LoginScreens.route)
                            } else {
                                navController.navigate(Screens.CartProductScreens.route){
                                    launchSingleTop = true
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        },
                        icon = { Icon(Icons.Filled.ShoppingCart, "Моя корзина") },
                        colors = ItemColors
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Мои заказы", fontSize = 20.sp) },
                        selected = false,
                        onClick = {
                            if (User.Id.id == -1) {
                                navController.navigate(Screens.LoginScreens.route)
                            } else {
                                navController.navigate(Screens.AllOrderPage.route){
                                    launchSingleTop = true
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        },
                        icon = { Icon(Icons.Filled.Done, "Мои заказы") },
                        colors = ItemColors
                    )
                }
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(8.dp), verticalArrangement = Arrangement.Bottom
//                ) {
//                    NavigationDrawerItem(
//                        modifier = Modifier.padding(8.dp),
//                        label = { Text("О нас", fontSize = 20.sp) },
//                        selected = false,
//                        onClick = {
//                            navController.navigate(Screens.AboutUsScreens.route)
//                            scope.launch {
//                                drawerState.close()
//                            }
//                        },
//                        icon = { Icon(Icons.Filled.Info, "О нас") },
//                        colors = ItemColors
//                    )
//                }
            }

        },
        scrimColor = Color.LightGray
    )
}
