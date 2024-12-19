package com.example.bytecity.view.ProductPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bytecity.model.MainColor

@Composable
fun ProductAttribute(key: String, value: String) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(MainColor.AppColor.value, Color.LightGray)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(4.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .padding(2.dp)
            .border(1.dp, Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.2f),
            text = key,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.Black)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProductAttributePreview() {
    ProductAttribute(key = "Power", value = "400")

}