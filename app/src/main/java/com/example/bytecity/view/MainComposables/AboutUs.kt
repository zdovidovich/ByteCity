package com.example.bytecity.view.MainComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AboutUsPage(scope: CoroutineScope, drawerState: DrawerState) {
    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            })
        }
    ) {
        Column(modifier=Modifier.fillMaxSize().padding(it)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Добро пожаловать в ",
                    fontSize = 24.sp
                )
                Text(
                    "ByteCity",
                    fontSize = 24.sp, fontFamily = FontFamily.Serif, fontStyle = FontStyle.Italic,

                )
                Text(
                    "!",
                    fontSize = 24.sp
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Здесь вы можете найти любые комплектующие по самым низким ценам в Беларуси! Мы только недавно открылись и будем рады любым покупателям.\n" +
                        "\n" +
                        "На данный момент мы не осуществляем доставки по всей Беларуси. Единственный способ купить товар - заказать его с доставкой в наш главный офис по адресу: г. Гродно, ул. Кабяка, д. 26.\n" +
                        "\n" +
                        "Мы работаем над возможностью доставки по всей Беларуси. Приятных покупок!",
                fontSize = 20.sp
            )


        }
    }
}


