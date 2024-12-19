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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.model.MainColor
import com.example.bytecity.model.Screens
import com.example.bytecity.view.MainComposables.TopBar
import com.example.bytecity.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginPage(navController: NavHostController) {
    val snackboxHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.padding(8.dp),
        snackbarHost = {
            SnackbarHost(snackboxHostState) { data ->
                Snackbar(
                    containerColor = Color(0xFF1D5DAA),
                    contentColor = Color.Black
                ) {
                    Text(text = data.visuals.message)
                }
            }
        },
        topBar = {
            TopBar {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(Icons.Filled.ArrowBack, "Назад")
                }
            }
        }

    ) {

        val loginViewModel = LoginViewModel()

        var passwordVisible by remember {
            mutableStateOf(false)
        }
        var login by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        val textFieldColor = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = MainColor.BackgroundColor.value,
            unfocusedContainerColor = MainColor.BackgroundColor.value,
            disabledContainerColor = MainColor.BackgroundColor.value,
        )
        val scopeSnackBar = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.padding(it))
            Text(
                "Добро пожаловать!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                "Вход",
                fontSize = 32.sp,
                color = Color(0xFF565C66),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                "через логин",
                fontSize = 16.sp,
                color = Color(0xFF565C66)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(), value = login,
                onValueChange = { login = it },
                label = { Text("Логин") },
                colors = textFieldColor,
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(modifier = Modifier.fillMaxWidth(), value = password,
                onValueChange = { password = it },
                label = {
                    Text("Пароль")
                },
                colors = textFieldColor,
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
                        scopeSnackBar.launch(Dispatchers.IO) {
                            val resCode = loginViewModel.checkData(login, password)
                            if (resCode != 200) {
                                snackboxHostState.showSnackbar(when(resCode){
                                    0 -> "Вы ввели пустой логин"
                                    1 -> "Вы ввели пустой пароль"
                                    2 -> "Неправильной логин или пароль"
                                    else -> "Error"
                                }, duration = SnackbarDuration.Short)
                            } else {
                                //TODO
                                withContext(Dispatchers.Main){
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
                    Text("Войти")
                }
                TextButton(onClick = {
                    navController.navigate(Screens.RegistrationScreens.route)
                }) {
                    Text(
                        "Зарегистрироваться",
                        color = Color(0xFF3061AF)
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
//    LoginPage()
}