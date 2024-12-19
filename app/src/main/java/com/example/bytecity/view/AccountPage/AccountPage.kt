package com.example.bytecity.view.AccountPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.model.User
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AccountPage(scope:CoroutineScope, drawerState: DrawerState, navHostController: NavHostController) {
    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }
                }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            })
        }
    ) {
        var openDialog by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = User.Id.login,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Email: ${User.Id.email}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Телефон ${User.Id.contactNumber}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(
                onClick = { openDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value)
            ) {
                Text("Выйти из аккаунта")
            }
            if (openDialog) {
                AlertDialog(
                    onDismissRequest = { openDialog = false },
                    title = { Text("Подтвердите действие", color = Color.White) },
                    confirmButton = {
                        Button(
                            onClick = {
                                User.Id.exit()
                                openDialog = false
                                navHostController.navigate(Screens.MainContentScreens.route){
                                    popUpTo(Screens.MainContentScreens.route){
                                        inclusive = true
                                    }
                                }
//                                TODO NAVIGATE TO SOMETHING
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Выход", color = MainColor.AppColor.value)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Отмена", color = MainColor.AppColor.value)
                        }
                    },
                    text = {
                        Text("Вы точно хотите выйти из аккаунта?", color = Color.White)
                    },
                    containerColor = MainColor.AppColor.value,

                    )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
//    AccountPage()
}