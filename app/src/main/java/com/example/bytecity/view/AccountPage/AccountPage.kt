package com.example.bytecity.view.AccountPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.model.User
import com.example.bytecity.view.MainComposables.MainSnackbar
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AccountPage(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController
) {
    val accountViewModel: AccountViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }
                }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            })
        },
        snackbarHost = {
            MainSnackbar(snackbarHostState = snackbarHostState)
        }
    ) {
        var openDialogExit by remember {
            mutableStateOf(false)
        }
        var openDialogChangeEmail by remember {
            mutableStateOf(false)
        }
        var openDialogChangePassword by remember {
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
                text = "Телефон: ${User.Id.contactNumber}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { openDialogChangeEmail = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Изменить Email")
            }

            Button(
                onClick = { openDialogChangePassword = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Изменить пароль")
            }

            Button(
                onClick = { openDialogExit = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Выйти из аккаунта")
            }



            if (openDialogChangeEmail) {
                var newEmail by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { openDialogChangeEmail = false },
                    title = { Text("Подтвердите действие", color = Color.White) },
                    confirmButton = {
                        Button(
                            onClick = {
                                scope.launch {
                                    when (accountViewModel.changeEmail(newEmail = newEmail)) {
                                        200 -> {
                                            openDialogChangeEmail = false
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Успешно!"
                                            )
                                        }
                                        3 -> {
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Неправильный email"
                                            )
                                        }
                                        else -> {
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Ошибка, повторите позже"
                                            )
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Выход", color = MainColor.AppColor.value)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialogChangeEmail = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Отмена", color = MainColor.AppColor.value)
                        }
                    },
                    text = {
                        TextField(
                            value = newEmail,
                            onValueChange = {
                                if (it.length < 41) {
                                    newEmail = it
                                }
                            },
                            label = { Text("Введите новый email") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            singleLine = true
                        )
                    },
                    containerColor = MainColor.AppColor.value
                )
            }


            if (openDialogChangePassword) {
                var errColor by remember { mutableStateOf(Color.White) }
                var buttonEnabled by remember { mutableStateOf(false) }
                var oldPassword by remember { mutableStateOf("") }
                var newPassword by remember { mutableStateOf("") }
                var newPasswordRepeat by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { openDialogExit = false },
                    title = { Text("Смена пароля", color = Color.White) },
                    confirmButton = {
                        Button(
                            enabled = buttonEnabled,
                            onClick = {
                                scope.launch {
                                    if (newPassword.length < 8) {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarHostState.showSnackbar(
                                            message = "Маленький новый пароль"
                                        )
                                    }

                                    when (accountViewModel.changePassword(
                                        oldPassword,
                                        newPassword
                                    )) {
                                        2 -> {
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Не правильный изначальный пароль"
                                            )
                                        }

                                        200 -> {

                                            openDialogChangePassword = false
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Успешно!"
                                            )
                                        }

                                        else -> {
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(
                                                message = "Ошибка, повторите позже"
                                            )
                                        }
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Сменить", color = MainColor.AppColor.value)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialogChangePassword = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Отмена", color = MainColor.AppColor.value)
                        }
                    },
                    text = {
                        Column(modifier = Modifier) {

                            TextField(
                                value = oldPassword,
                                onValueChange = {
                                    if (it.length < 50) {
                                        oldPassword = it
                                    }
                                },
                                label = { Text("Введите старый пароль") },
                                visualTransformation = PasswordVisualTransformation(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            TextField(
                                value = newPassword,
                                onValueChange = {
                                    if (it.length < 50) {
                                        newPassword = it
                                    }
                                },
                                label = { Text("Введите новый пароль") },
                                visualTransformation = PasswordVisualTransformation(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            TextField(
                                value = newPasswordRepeat,
                                onValueChange = {
                                    if (it.length < 50) {
                                        newPasswordRepeat = it
                                    }

                                    if (newPassword != newPasswordRepeat) {
                                        errColor = Color(0xFFEB5454)
                                        buttonEnabled = false
                                    } else {
                                        errColor = Color.White
                                        buttonEnabled = true
                                    }
                                },
                                label = { Text("Повторите новый пароль") },
                                visualTransformation = PasswordVisualTransformation(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = errColor,
                                    unfocusedContainerColor = errColor
                                ),
                                singleLine = true
                            )
                        }
                    },
                    containerColor = MainColor.AppColor.value
                )
            }

            if (openDialogExit) {
                AlertDialog(
                    onDismissRequest = { openDialogExit = false },
                    title = { Text("Подтвердите действие", color = Color.White) },
                    confirmButton = {
                        Button(
                            onClick = {
                                User.Id.exit()
                                openDialogExit = false
                                navHostController.navigate(Screens.MainContentScreens.route) {
                                    popUpTo(Screens.MainContentScreens.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Выход", color = MainColor.AppColor.value)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialogExit = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("Отмена", color = MainColor.AppColor.value)
                        }
                    },
                    text = {
                        Text("Вы точно хотите выйти из аккаунта?", color = Color.White)
                    },
                    containerColor = MainColor.AppColor.value
                )
            }


        }
    }
}