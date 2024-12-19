package com.example.bytecity.view.CategoryPage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bytecity.view.MainComposables.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryList(categories: Map<String, String>, drawerState: DrawerState, scope:CoroutineScope, navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        topBar = {
            TopBar {
                IconButton(onClick = {
                    scope.launch{
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, "Меню")
                }
            }
        },
    ) {
        Spacer(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 8.dp)
        ) {
            items(categories.toList()) { category ->
                CategoryItem(category = category, navController = navController)
            }
        }
    }
}