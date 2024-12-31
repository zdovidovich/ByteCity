package com.example.bytecity.model

import androidx.compose.ui.graphics.Color

sealed class User(var id: Int, var login: String, var email: String, var contactNumber: String) {
    data object Id : User(-1, "", "", "")


    fun exit() {
        Id.id = -1
        Id.login = ""
        Id.email = ""
        Id.contactNumber = ""
    }
}

sealed class Category(val categories: Map<String, String>) {
    data object MainList : Category(
        mapOf(
            "Телефоны" to "Phone",
            "Компьюетры" to "PC",
            "Комплектующие" to "Components",
            "Все товары" to ""
        )
    )

    data object Components : Category(
        mapOf(
            "Материнские платы" to "Motherboard",
            "Процессоры" to "Processor",
            "Оперативная память" to "Ram",
            "Видеокарты" to "Videocard",
            "SSD" to "Ssd",
            "HDD" to "Hdd",
            "Охлаждение процессора" to "Cooling",
            "Корпуса" to "PCCase",
            "Блоки питания" to "PowerUnit"
        )
    )
}


sealed class Screens(val route:String) {
    data object MainContentScreens: Screens("maincontentpage")
    data object LoginScreens: Screens("loginpage")
    data object RegistrationScreens: Screens("registrationpage")
    data object ProductPageScreens: Screens("productpage")
    data object CategoryScreens: Screens("categorypage")
    data object ListProductsScreens: Screens("listproductitemspage")
    data object FavouriteProductScreens: Screens("favouritepage")
    data object CartProductScreens: Screens("cartpage")
    data object AccountPage: Screens("accountpage")
    data object MakeOrderPage: Screens("makeorderpage")
    data object AllOrderPage:Screens("allorderpage")
    data object OrderPage:Screens("orderpage")
    data object SearchPage:Screens("searchpage")
    data object ReviewPage:Screens("reviewpage")
    data object MakeReviewPage:Screens("makereviewpage")
}

sealed class ScreenWithModalNavigationDrawer(val list: List<String>){
    data object AllScreens: ScreenWithModalNavigationDrawer(listOf(
        Screens.MainContentScreens.route,
        Screens.CategoryScreens.route,
        Screens.ListProductsScreens.route,
        Screens.FavouriteProductScreens.route,
        Screens.CartProductScreens.route,
        Screens.AccountPage.route,
        Screens.AllOrderPage.route
    ))
}



sealed class MainColor(val value: Color){
    data object BackgroundColor: MainColor(Color.White)
    data object AppColor: MainColor(Color(0xFF1D5DAA))
}


