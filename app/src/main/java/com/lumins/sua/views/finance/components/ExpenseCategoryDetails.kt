package com.lumins.sua.views.finance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.data.local.db.UserExpense
import java.util.Calendar
import java.util.Locale

@Composable
fun ExpenseCategoryDetails(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    categoryExpenses: List<UserExpense>,
    onDelete: (UserExpense) -> Unit
) {
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

            categoryExpenses.map { expense ->
                val calendar =
                    Calendar.getInstance().apply { timeInMillis = expense.datetime.toLong() }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "${expense.name}", fontWeight = FontWeight.Bold)
                            Row {
                                Row {
                                    Icon(
                                        imageVector = Icons.Rounded.AttachMoney,
                                        contentDescription = "amount"
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "${expense.amount}",
//                                        style = MaterialTheme.typography.labelLarge
                                    )

                                }
                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = "${calendar.get(Calendar.DAY_OF_MONTH)} ${
                                        calendar.getDisplayName(
                                            Calendar.MONTH, Calendar.SHORT, Locale.getDefault()
                                        )
                                    }", style = MaterialTheme.typography.labelLarge
                                )

                            }
                        }

                        IconButton(onClick = { onDelete(expense) }) {
                            Icon(
                                imageVector = Icons.Rounded.DeleteForever,
                                contentDescription = "delete",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
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