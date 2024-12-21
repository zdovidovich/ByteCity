package com.example.bytecity.view.MakeReviewPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.model.MainColor
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun MakeReviewPage(
    idProduct:Int,
    navHostController: NavHostController
) {
    val makeReviewViewModel: MakeReviewViewModel = viewModel()


    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(navIcon = {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)

        ) {
            val sliderPosition = remember { mutableStateOf(5f) }
            val review = remember {
                mutableStateOf("")
            }

            val scope = rememberCoroutineScope()

            Text("Оставить отзыв товару", fontSize = 24.sp)
            Text(text = "Оценка: ${sliderPosition.value}", fontSize = 20.sp)
            Slider(
                value = sliderPosition.value, onValueChange = {
                    sliderPosition.value = (it * 10).roundToInt() / 10f
                },
                valueRange = 0f..5f,
                steps = 49,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = review.value,
                onValueChange = { review.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Button(
                onClick = {
                            scope.launch {
                                makeReviewViewModel.uploadReview(
                                    idProduct,
                                    sliderPosition.value,
                                    review.value
                                )
                                navHostController.navigateUp()
                            }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor.AppColor.value)
            ) {
                Text(text = "Выложить отзыв")
            }
        }

    }
}