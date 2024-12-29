package com.example.bytecity.view.MainComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bytecity.model.MainColor
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navIcon: @Composable () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    "ByteCity",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic
                )
            }
        },
        navigationIcon = { navIcon() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MainColor.AppColor.value,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navIcon: @Composable () -> Unit, actionIcon: @Composable () -> Unit) {
    TopAppBar(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    "ByteCity",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic
                )
            }
        },
        navigationIcon = {
            navIcon()

        },
        actions = {
            actionIcon()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MainColor.AppColor.value,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchingTopBar(
    text: MutableState<String>,
    textForQuery: MutableStateFlow<String>,
    iconButton: @Composable () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                iconButton()
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                    value = text.value, onValueChange = {
                        text.value = it
                        textForQuery.value = it
                    }, singleLine = true, placeholder = {
                        Text(text = "Поиск")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MainColor.AppColor.value,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
