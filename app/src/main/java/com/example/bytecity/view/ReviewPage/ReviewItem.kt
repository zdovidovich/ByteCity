package com.example.bytecity.view.ReviewPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bytecity.businessClasses.Review
import com.example.bytecity.model.MainColor

@Composable
fun ReviewItem(review: Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MainColor.AppColor.value)
    ) {
            Text(text = review.login, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.White)
            Row {
                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
                Text(
                    text = "${review.rating}",
                    color = Color.Yellow,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                )
            }
            Text(text = review.review, modifier = Modifier.fillMaxWidth(), color = Color.White)
    }
}