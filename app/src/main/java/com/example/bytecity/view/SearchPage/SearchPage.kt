package com.example.bytecity.view.SearchPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bytecity.model.MainColor
import com.example.bytecity.view.ListProductsPage.ProductItemPreScreen
import com.example.bytecity.view.MainComposables.MainSnackbar
import com.example.bytecity.view.MainComposables.SearchingTopBar
import kotlinx.coroutines.launch

@Composable
fun SearchPage(navHostController: NavHostController) {
    val text = remember {
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        topBar = {
            SearchingTopBar(text = text) {
                IconButton(onClick = { navHostController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                }
            }
        },
        snackbarHost = {
            MainSnackbar(snackbarHostState = snackbarHostState)
        }
    ) {
        val viewModel: SearchPageViewModel = viewModel()
        val context = LocalContext.current
        LaunchedEffect(text.value) {
            viewModel.getProducts(text.value, context)
        }

        val products = viewModel.pager.collectAsLazyPagingItems()
        val listState = rememberLazyGridState()
        val scope = rememberCoroutineScope()



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                products.loadState.refresh is LoadState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                products.loadState.refresh is LoadState.Error -> {
                    Text(text = "Ошибка, повторите позже", modifier=Modifier.align(
                        Alignment.Center), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                text.value.isEmpty() -> {
                    Text(text = "Введите что-нибудь в поисковике :)", modifier=Modifier.align(
                        Alignment.Center), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                products.itemCount == 0 -> {
                    Text(text = "Ничего не найдено", modifier=Modifier.align(
                        Alignment.Center), fontSize = 16.sp, fontWeight = FontWeight.Medium)

                }
                else -> {
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(2), modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(products.itemCount) { index ->
                            ProductItemPreScreen(
                                product = products[index]!!,
                                navHostController = navHostController,
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                listState.scrollToItem(0)
                            }
                        }, modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        containerColor = MainColor.AppColor.value
                    ) {
                        Icon(Icons.Filled.KeyboardArrowUp, "Наверх", tint = Color.White)
                    }

                }
            }
        }
    }
}