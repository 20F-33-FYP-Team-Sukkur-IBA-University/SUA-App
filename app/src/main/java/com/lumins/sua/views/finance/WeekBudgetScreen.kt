package com.lumins.sua.android.views.finance

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.R
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.ui.theme.SuaColors
import com.lumins.sua.views.finance.components.BudgetCategoryCard
import com.lumins.sua.views.finance.components.ExpenseCategoryDetails
import com.lumins.sua.views.finance.components.charts.PieChart

data class ExpenseCategory(val title: String, val color: Color, val percentage: Float, val image: Int)


@Composable
fun SingleWeekFinanceScreen() {
    val expenseCategories = remember { mutableStateListOf(
        ExpenseCategory("Food", SuaColors.darkRedMisc, 0.15f, R.drawable.icon_food),
        ExpenseCategory("Shopping", SuaColors.lightGreenMisc, 0.45f, R.drawable.icon_cart),
        ExpenseCategory("Bitcoin", SuaColors.primaryBlue, 0.20f, R.drawable.icon_bitcoin),
        ExpenseCategory("Transport", SuaColors.primaryBlueVariant, 0.20f, R.drawable.icon_transport),
    ) }
    var selectedCategory by remember { mutableStateOf("Food") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            items(expenseCategories) { category ->
                BudgetCategoryCard(
                    modifier = Modifier
                        .height(160.dp)
                        .width(136.dp)
                        .padding(start = 6.dp),
                    imageResourceId = category.image,
                    categoryColor = category.color,
                    categoryTitle = category.title,
                    categoryPercentage = (category.percentage * 100).toInt()
                ) {
                    // when card is clicked
                    selectedCategory = category.title

                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This Week",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier =  Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                modifier = Modifier
                    .size(240.dp)
                    .padding(16.dp),
                strokeWidth = 50f,
                selectedStrokeWidth = 80f,
                title = "3,400\nTotal Expense",
                entries = expenseCategories,
                selectedCategory = selectedCategory
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Expense Detail",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier =  Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExpenseCategoryDetails(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            categoryTitle = selectedCategory
        )

    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    SUATheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SingleWeekFinanceScreen()
        }
    }
}