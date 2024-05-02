package com.lumins.sua.views.finance.components

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.R
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.data.local.db.UserExpense
import com.lumins.sua.data.model.ExpenseType
import com.lumins.sua.repo.SuaRepository
import com.lumins.sua.ui.theme.SuaColors
import com.lumins.sua.views.finance.ExpenseCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class FinanceReportViewModel(context: Context, cardType: FinanceCardType) : ViewModel() {
    private val repo = SuaRepository(DatabaseDriverFactory(context))

    private val _expenses = MutableStateFlow(emptyList<UserExpense>())
    val expenses = _expenses.asStateFlow()


    private val _currentExpenses = MutableStateFlow(emptyList<UserExpense>())
    val currentExpenses = _currentExpenses.asStateFlow()

    private var _expenseCategories = MutableStateFlow<List<ExpenseCategory>>(emptyList())
    val expenseCategories = _expenseCategories.asStateFlow()

    init {
        viewModelScope.launch {
            loadExpenses(cardType)
        }
    }

    private fun loadExpenses(cardType: FinanceCardType) {
        _expenses.value = repo.getAllExpenses()
        _currentExpenses.value = when (cardType) {
            FinanceCardType.TODAY -> {
                _expenses.value.filter {
                    val calendar =
                        Calendar.getInstance().apply { timeInMillis = it.datetime.toLong() }
                    calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance()
                        .get(Calendar.DAY_OF_YEAR)
                }
            }

            FinanceCardType.WEEK -> {
                _expenses.value.filter {
                    val calendar =
                        Calendar.getInstance().apply { timeInMillis = it.datetime.toLong() }
                    calendar.get(Calendar.WEEK_OF_YEAR) == Calendar.getInstance()
                        .get(Calendar.WEEK_OF_YEAR)
                }
            }

            FinanceCardType.MONTH -> {
                _expenses.value.filter {
                    val calendar =
                        Calendar.getInstance().apply { timeInMillis = it.datetime.toLong() }
                    calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
                }
            }
        }

        _expenseCategories.value = listOf(
            ExpenseCategory(
                "Food",
                SuaColors.darkRedMisc,
                calculateCategoryPercentage(ExpenseType.FOOD),
                R.drawable.icon_food
            ),
            ExpenseCategory(
                "Shopping",
                SuaColors.lightGreenMisc,
                calculateCategoryPercentage(ExpenseType.SHOPPING),
                R.drawable.icon_cart
            ),
            ExpenseCategory(
                "Health",
                SuaColors.primaryBlue,
                calculateCategoryPercentage(ExpenseType.HEALTH),
                R.drawable.home_health
            ),
            ExpenseCategory(
                "Transport",
                SuaColors.primaryBlueVariant,
                calculateCategoryPercentage(ExpenseType.TRANSPORT),
                R.drawable.icon_transport
            ),
            ExpenseCategory(
                "Other",
                SuaColors.goldenYellow,
                calculateCategoryPercentage(ExpenseType.OTHER),
                R.drawable.asterisk
            ),
        )
    }
    private fun calculateCategoryPercentage(type: ExpenseType): Float {
        return _currentExpenses.value.filter { it.expense_type == type }.sumOf { it.amount }
            .toFloat() / _currentExpenses.value.sumOf { it.amount }
    }

    fun deleteExpense(expense: UserExpense, cardType: FinanceCardType) {
        viewModelScope.launch {
            repo.deleteExpense(expense)
            loadExpenses(cardType)
        }
    }

    fun getExpensesByCategory(selectedCategory: ExpenseCategory): List<UserExpense> {
        return when (selectedCategory.title) {
            "Food" -> {
                _currentExpenses.value.filter { it.expense_type == ExpenseType.FOOD }
            }

            "Shopping" -> {
                _currentExpenses.value.filter { it.expense_type == ExpenseType.SHOPPING }
            }

            "Health" -> {
                _currentExpenses.value.filter { it.expense_type == ExpenseType.HEALTH }
            }

            "Transport" -> {
                _currentExpenses.value.filter { it.expense_type == ExpenseType.TRANSPORT }
            }

            "Other" -> {
                _currentExpenses.value.filter { it.expense_type == ExpenseType.OTHER }
            }

            else -> {
                emptyList()
            }
        }
    }


}