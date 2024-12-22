package com.example.bytecity.view.MainComposables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bytecity.model.MainColor

@Composable
fun MainSnackbar(snackbarHostState:SnackbarHostState){
    SnackbarHost(snackbarHostState) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = MainColor.AppColor.value,
            contentColor = Color.White,
            shape = RoundedCornerShape(8.dp),
            modifier= Modifier.padding(16.dp)
        )
    }
}