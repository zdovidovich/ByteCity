package com.example.bytecity.view.LoginAndRegistrationPages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.model.MainColor
import com.example.bytecity.view.MainComposables.TopBar
import com.example.bytecity.viewmodel.RegistrationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RegistrationPage(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.padding(8.dp),
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = MainColor.AppColor.value, contentColor = Color.Black
                ) {
                    Text(data.visuals.message)
                }
            }
        },
        topBar = {
            TopBar {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }
        }) {
        val registrationViewModel = RegistrationViewModel()

        val scopeSnackbar = rememberCoroutineScope()

        val backgroundColor = Color.White

        val textFiledColors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
        )


        var passwordVisible by remember {
            mutableStateOf(false)
        }
        var login by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var contactnumber by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.padding(it))
            Text(
                "Добро пожаловать!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                "Регистрация",
                fontSize = 24.sp,
                color = Color(0xFF565C66),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(), value = login,
                onValueChange = {
                    login = it
                },
                label = { Text("Логин") },
                colors = textFiledColors,
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(), value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email") },
                colors = textFiledColors,
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(), value = contactnumber,
                onValueChange = {
                    contactnumber = it
                },
                label = { Text("Телефон") },
                colors = textFiledColors,
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(modifier = Modifier.fillMaxWidth(), value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Пароль")
                },
                colors = textFiledColors,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(Icons.Filled.CheckCircle, "Check")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row {
                Button(
                    onClick = {
                        scopeSnackbar.launch(Dispatchers.IO) {
                            val resCode = registrationViewModel.addUser(
                                login = login,
                                email = email,
                                contactNumber = contactnumber,
                                password = password
                            )
                            if (resCode != 200) {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    when (resCode) {
                                        0 -> "Вы ввели уже зарегистрированный email"
                                        1 -> "Вы ввели уже зарегистрированный логин"
                                        2 -> "Слишком маленький пароль(минимум 8 символов)"
                                        3 -> "Неправильный email"
                                        4 -> "Неправильный контактный телефон"
                                        5 -> "Вы ввели уже зарегестрированный телефон"
                                        6 -> "Вы ввели пустой логин"
                                        else -> "ERROR"
                                    }, duration = SnackbarDuration.Short
                                )
                            } else {
                                withContext(Dispatchers.Main) {
                                    navController.navigateUp()
                                }
                            }
                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3061AF)
                    )
                ) {
                    Text("Зарегестрироваться")
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun RegistrationPreview() {
//    RegistrationPage()
//}