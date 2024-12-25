package com.example.bytecity.view.ReviewPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.Review
import com.example.bytecity.view.MainComposables.TopBar


@Composable
fun ReviewPreScreen(idProduct: Int, navHostController: NavHostController) {
    val reviewViewModel: ReviewViewModel = viewModel()
    reviewViewModel.getReviews(idProduct)

    val viewState by reviewViewModel.reviewState
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            !viewState.error.isNullOrEmpty() -> {
                Text(text = viewState.error!!)
            }

            else -> {
                ReviewPage(viewState.reviews, navHostController)
            }
        }

    }
}


@Composable
fun ReviewPage(
    reviews: List<Review>, navHostController: NavHostController
) {
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
                .fillMaxSize()
                .padding(it)
        ) {

            Text("Отзывы", fontSize = 30.sp, fontWeight = FontWeight.Medium)
            LazyColumn {
                items(reviews) { review ->
                    ReviewItem(review = review)
                }
            }
        }
    }

}