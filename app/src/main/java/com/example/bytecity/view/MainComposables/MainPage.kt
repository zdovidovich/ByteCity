package com.example.bytecity.view.MainComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.model.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MainContentPage(
    navHostController: NavHostController,
    scope: CoroutineScope, drawerState: DrawerState
) {
    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            }, actionIcon = {
                IconButton(onClick = {
                    navHostController.navigate(Screens.SearchPage.route)
                }) {
                    Icon(Icons.Filled.Search, "Поиск")
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)

        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Добро пожаловать в ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    "ByteCity",
                    fontSize = 24.sp, fontFamily = FontFamily.Serif, fontStyle = FontStyle.Italic,

                    )
                Text(
                    "!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Здесь вы можете найти любые комплектующие по самым низким ценам в Беларуси! Мы только недавно открылись и будем рады любым покупателям.\n" +
                        "\n" +
                        "На данный момент мы не осуществляем доставки по всей Беларуси. Единственный способ купить товар - заказать его с доставкой в наш главный офис по адресу: г. Гродно, ул. Кабяка, д. 26.\n" +
                        "\n" +
                        "Мы работаем над возможностью доставки по всей Беларуси. Приятных покупок!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )


        }

    }

}


@Preview(showBackground = true)
@Composable
fun MainPreview() {

}