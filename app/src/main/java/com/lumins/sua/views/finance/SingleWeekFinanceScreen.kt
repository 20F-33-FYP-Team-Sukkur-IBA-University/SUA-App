package com.lumins.sua.views.finance

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.views.finance.components.BudgetCategoryCard
import com.lumins.sua.views.finance.components.ExpenseCategoryDetails
import com.lumins.sua.views.finance.components.FinanceCardType
import com.lumins.sua.views.finance.components.FinanceReportViewModel
import com.lumins.sua.views.finance.components.charts.PieChart

data class ExpenseCategory(val title: String, val color: Color, val percentage: Float, val image: Int)


@Composable
fun SingleWeekFinanceScreen(type: FinanceCardType) {
    val context = LocalContext.current
    val viewModel: FinanceReportViewModel = viewModel(factory = viewModelFactory { FinanceReportViewModel(context, type) })
    val expenses by viewModel.currentExpenses.collectAsStateWithLifecycle()
    val expenseCategories = viewModel.expenseCategories.collectAsStateWithLifecycle()

    var selectedCategoryIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            items(expenseCategories.value) { category ->
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
                    selectedCategoryIndex = expenseCategories.value.indexOf(category)

                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = when(type) {
                FinanceCardType.TODAY -> "Today"
                FinanceCardType.WEEK -> "This Week"
                FinanceCardType.MONTH -> "This Month"
            },
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
                title = "${viewModel.getExpensesByCategory(expenseCategories.value[selectedCategoryIndex]).sumOf { it.amount }}\nTotal Expense",
                entries = expenseCategories,
                selectedCategory = expenseCategories.value[selectedCategoryIndex].title
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Expenses Details",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier =  Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExpenseCategoryDetails(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            categoryTitle = expenseCategories.value[selectedCategoryIndex].title,
            categoryExpenses = viewModel.getExpensesByCategory(expenseCategories.value[selectedCategoryIndex])
        ) {
            viewModel.deleteExpense(it, type)
        }

    }
}

