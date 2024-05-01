package com.lumins.sua.views.finance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseCategoryDetails(
    modifier: Modifier  = Modifier,
    categoryTitle: String
)  {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = categoryTitle, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(8.dp))


            (0..12).toList().map { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Item $index", modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "${index + 4}", modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "${index + 6}", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewExpenseCategoryDetails() {
//    MyApplicationTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White.copy(0.7f)),
//            contentAlignment = Alignment.Center
//        ) {
//            ExpenseCategoryDetails()
//        }
//    }
//}