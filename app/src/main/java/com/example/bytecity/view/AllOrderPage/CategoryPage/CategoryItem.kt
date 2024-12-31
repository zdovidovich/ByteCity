package com.example.bytecity.view.AllOrderPage.CategoryPage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.model.Category
import com.example.bytecity.model.Screens

@Composable
fun CategoryItem(category: Pair<String, String>, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(2.dp, Color.Black)
            .clickable {
                if (category.second == Category.MainList.categories["Комплектующие"]) {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "cat",
                        Category.Components.categories
                    )
                    navController.navigate(Screens.CategoryScreens.route)
                } else {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "catPage",
                        category.second
                    )
                    navController.navigate(Screens.ListProductsScreens.route)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = category.first,
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .padding(start = 4.dp)
                .padding(vertical = 8.dp)
        )
        Icon(Icons.Filled.PlayArrow, "Дальше")
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun CategoryItemPreview() {
//    CategoryItem(category = "Телефоны")
//}