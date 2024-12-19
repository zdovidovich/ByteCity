package com.example.bytecity.view.AllOrderPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bytecity.businessClasses.Order
import com.example.bytecity.model.Screens

@Composable
fun AllOrderItem(
    order: Order,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable {
                navHostController.currentBackStackEntry?.savedStateHandle?.set("order", order)
                navHostController.navigate(Screens.OrderPage.route)
            }
            .padding(16.dp))
    {
        Text(
            text = "Имя заказчика: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text =
            order.name, fontSize = 18.sp, fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = "Дата заказа: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = order.registrationDate.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "Статус: ", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(text = order.status, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = "Список товаров: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Column(modifier = Modifier.padding(top = 4.dp)) {
            for (product in order.products) {
                Text(
                    text = "${product.product.brand} ${product.product.model} - Количество: ${product.qty}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}