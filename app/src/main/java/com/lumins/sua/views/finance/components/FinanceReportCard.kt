package com.lumins.sua.views.finance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.android.utils.formatWithCommas
import com.lumins.sua.data.local.db.UserExpense
import java.util.Calendar


@Composable
fun FinanceReportCard(
    modifier: Modifier = Modifier,
    expenses: List<UserExpense>,
    todayAmount: Int,
    weekAmount: Int,
    monthAmount: Int,
    onCardClick: (FinanceCardType) -> Unit
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize().padding(vertical = 8.dp)
        ) {
            Text(
                text = "Expense Report",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                SimpleClickableCard(
                    modifier = Modifier
                        .height(140.dp)
                        .width(110.dp),
                    title = "Today", amount = todayAmount) { onCardClick(FinanceCardType.TODAY) }
                SimpleClickableCard(
                    modifier = Modifier
                        .height(140.dp)
                        .width(120.dp),
                    title = "This Week", amount = weekAmount) { onCardClick(FinanceCardType.WEEK) }
                SimpleClickableCard(
                    modifier = Modifier
                        .height(140.dp)
                        .width(120.dp),
                    title = "This Month", amount = monthAmount) { onCardClick(FinanceCardType.MONTH) }
            }
        }
    }
}

@Composable
private fun SimpleClickableCard(
    modifier: Modifier = Modifier,
    title: String,
    amount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onClick() },
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = amount.formatWithCommas(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


enum class FinanceCardType {
    TODAY, WEEK, MONTH
}
