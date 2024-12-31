package com.example.bytecity.Navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.businessClasses.Product
import com.example.bytecity.businessClasses.ProductForCart
import com.example.bytecity.model.Category
import com.example.bytecity.model.Screens
import com.example.bytecity.view.AccountPage.AccountPage
import com.example.bytecity.view.AllOrderPage.AllOrderPage
import com.example.bytecity.view.AllOrderPage.CategoryPage.CategoryList
import com.example.bytecity.view.CartPage.CartPreScreen
import com.example.bytecity.view.FavouritePage.FavouritePreScreen
import com.example.bytecity.view.ListProductsPage.ListProductPage
import com.example.bytecity.view.LoginAndRegistrationPages.LoginPage
import com.example.bytecity.view.LoginAndRegistrationPages.RegistrationPage
import com.example.bytecity.view.MainComposables.MainContentPage
import com.example.bytecity.view.MakeOrderPage.makeOrderPage
import com.example.bytecity.view.MakeReviewPage.MakeReviewPage
import com.example.bytecity.view.OrderPage.OrderPage
import com.example.bytecity.view.ProductPage.ProductPreScreen
import com.example.bytecity.view.ReviewPage.ReviewPreScreen
import com.example.bytecity.view.SearchPage.SearchPage
import java.util.Date

@Composable
fun Navigation(navController: NavHostController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = Screens.MainContentScreens.route) {
        composable(route = Screens.MainContentScreens.route) {
            MainContentPage(
                navHostController = navController,
                scope = scope,
                drawerState = drawerState
            )
        }
        composable(route = Screens.LoginScreens.route) {
            LoginPage(navController = navController)
        }
        composable(route = Screens.RegistrationScreens.route) {
            RegistrationPage(navController = navController)
        }
        composable(route = Screens.CategoryScreens.route) {
            val cat =
                navController.previousBackStackEntry?.savedStateHandle?.get<Map<String, String>>("cat")
                    ?: Category.MainList.categories
            CategoryList(
                categories = cat,
                scope = scope,
                drawerState = drawerState,
                navController = navController
            )
        }
        composable(route = Screens.ListProductsScreens.route) {
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("catPage")
                    ?: Category.MainList.categories["Телефоны"]!!
            ListProductPage(
                type = category,
                scope = scope,
                drawerState = drawerState,
                navHostController = navController
            )
        }
        composable(route = Screens.ProductPageScreens.route) {
            val product =
                navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
            if (product == null) {
                navController.navigateUp()
                //TODO but i don't know how (smth's wrong with navigation)
            } else {
                ProductPreScreen(
                    product = product,
                    navHostController = navController,
                    scope = scope
                )
            }
        }
        composable(route = Screens.FavouriteProductScreens.route) {
            val productToDelete = navController.currentBackStackEntry?.savedStateHandle?.get<Product>("producttofavourite")
            FavouritePreScreen(
                scope = scope,
                drawerState = drawerState,
                navHostController = navController,
                idProductToDelete = productToDelete
            )
        }
        composable(route = Screens.CartProductScreens.route) { entry ->
            CartPreScreen(
                scope = scope,
                drawerState = drawerState,
                navHostController = navController
            )
        }
        composable(route = Screens.AccountPage.route) {
            AccountPage(scope = scope, drawerState = drawerState, navHostController = navController)
        }
        composable(route = Screens.MakeOrderPage.route) {
            val products =
                navController.previousBackStackEntry?.savedStateHandle?.get<List<ProductForCart>>("productsforcart")
                    ?: listOf()
            makeOrderPage(navHostController = navController, productsForCart = products)
        }
        composable(route = Screens.AllOrderPage.route) {
            AllOrderPage(drawerState = drawerState, scope = scope, navHostController = navController)
        }
        composable(route = Screens.OrderPage.route) {
            val order = navController.previousBackStackEntry?.savedStateHandle?.get<Order>("order")
                ?: Order(registrationDate = Date(), products = listOf(), status = "")
            OrderPage(order = order, navHostController = navController, scope = scope)
        }
        composable(route = Screens.SearchPage.route) {
            SearchPage(navHostController = navController)
        }
        composable(route = Screens.ReviewPage.route) {
            val product = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("productsforreview")
            if (product == null) {
                navController.navigateUp()
                //TODO but i don't know how (smth's wrong with navigation)
            } else {
                ReviewPreScreen(idProduct = product, navHostController = navController)
            }
        }
        composable(route = Screens.MakeReviewPage.route) {
            val productForMakeReview =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("productformakereview")
            if (productForMakeReview == null) {
                navController.navigateUp()
            } else {
                MakeReviewPage(
                    idProduct = productForMakeReview,
                    navHostController = navController
                )
            }
        }

    }

}
