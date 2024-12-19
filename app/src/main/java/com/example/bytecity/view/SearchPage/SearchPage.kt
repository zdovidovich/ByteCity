package com.example.bytecity.view.SearchPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bytecity.model.MainColor
import com.example.bytecity.view.ListProductsPage.ProductItemPreScreen
import com.example.bytecity.view.MainComposables.SearchingTopBar
import com.example.bytecity.viewmodel.SearchPageViewModel

@Composable
fun SearchPage(navHostController: NavHostController) {
    val text = remember {
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            SearchingTopBar(text=text) {
                IconButton(onClick = { navHostController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, "Назад")
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MainColor.AppColor.value,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(8.dp),
                )

            }
        }
    ) {
        val searchPageViewModel: SearchPageViewModel = viewModel()
        searchPageViewModel.findProducts(text.value)
        val viewState by searchPageViewModel.searchState
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                !viewState.error.isNullOrEmpty() -> {
                    Text(text = viewState.error.toString())
                }

                text.value.isEmpty() -> {
                    Text(text = "Введите что-нибудь в поисковике :)")
                }

                viewState.products.isEmpty() -> {
                    Text("Ничего не найдено")
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        items(viewState.products) { product ->
                            ProductItemPreScreen(
                                product = product,
                                navHostController = navHostController,
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
}