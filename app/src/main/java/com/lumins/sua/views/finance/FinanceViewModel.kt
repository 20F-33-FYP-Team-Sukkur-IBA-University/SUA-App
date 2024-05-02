package com.lumins.sua.views.finance

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.data.local.db.UserExpense
import com.lumins.sua.navigation.SuaScreen
import com.lumins.sua.repo.SuaRepository
import com.lumins.sua.utils.DataStoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class FinanceViewModel(context: Context): ViewModel() {
    private val prefs = DataStoreUtils
    private val repo = SuaRepository(DatabaseDriverFactory(context))

    private var _startScreen = MutableStateFlow(SuaScreen.Loading.route)
    val startScreen = _startScreen.asStateFlow()

    private var _balance = MutableStateFlow(0)
    val balance = _balance.asStateFlow()

    private var _expenses = MutableStateFlow<List<UserExpense>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private var _lineChartData = MutableStateFlow<LineChartData?>(null)
    val lineChartData = _lineChartData.asStateFlow()

    private var _todayAmount = MutableStateFlow(0)
    val todayAmount = _todayAmount.asStateFlow()

    private var _weekAmount = MutableStateFlow(0)
    val weekAmount = _weekAmount.asStateFlow()

    private var _monthAmount = MutableStateFlow(0)
    val monthAmount = _monthAmount.asStateFlow()

    init {
        viewModelScope.launch {
            _expenses.value = repo.getAllExpenses()
            _balance.value = prefs.getBalanceInt(context)

            prefs.getIsFinanceSetup(context).collectLatest { isFinanceSetup ->
                _startScreen.value = if (isFinanceSetup) {
                    SuaScreen.Finance.route
                } else {
                    SuaScreen.FinanceOnboarding.route
                }
            }
            updateExpenseChart()
            updateExpenseReport()
        }
    }

    fun updateExpenseReport() {
        _todayAmount.value = calculateTodayAmount(expenses.value)
        _weekAmount.value = calculateWeekAmount(expenses.value)
        _monthAmount.value = calculateMonthAmount(expenses.value)
    }

    fun financeOnboardingDone(context: Context, balance: Int) {
        viewModelScope.launch {
            prefs.saveIsFinanceSetup(context, true)
            prefs.saveBalanceInt(context, balance)
            _balance.value = prefs.getBalanceInt(context)
        }
    }

    fun updateBalance(context: Context, balance: Int) {
        viewModelScope.launch {
            prefs.saveBalanceInt(context, balance)
            _balance.value = prefs.getBalanceInt(context)
        }
    }

    fun addExpense(context: Context, expense: UserExpense) {
        viewModelScope.launch {
            repo.insertExpense(expense)
            updateBalance(context, balance.value - expense.amount)
            _expenses.value = repo.getAllExpenses()
            updateExpenseChart()
        }
    }

    fun updateExpenseChart() {

        val pointsData = _expenses.value.mapIndexed { i, expense -> getChartPoint(expense, i) }
        val steps = pointsData.size
        val xAxisData =
            AxisData.Builder().axisStepSize(80.dp).steps(steps).labelData { i -> i.toString() }
                .labelAndAxisLinePadding(15.dp).build()

        val yAxisData = AxisData.Builder().steps(steps).backgroundColor(Color.White)
            .labelAndAxisLinePadding(18.dp).labelData { i ->
                val yScale = (pointsData.maxOfOrNull { it.y } ?: 1000f) / steps
                (i * yScale).toInt().toString()
            }.build()

        _lineChartData.value = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(
                            lineType = LineType.SmoothCurve(),
                        ),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
        )

//        Log.d("TEST_TAG", "FinanceVM:  ${_lineChartData.value?.linePlotData?.lines?.size}")
    }

    private fun getChartPoint(expense: UserExpense, index: Int): Point {
        val calendar = Calendar.getInstance().apply { timeInMillis = expense.datetime.toLong() }
        return Point(
            x = index.toFloat(),
            y = expense.amount.toFloat(),
            description = "Expense: ${expense.name}\nAmount: ${expense.amount}\nDate: ${
                calendar.get(
                    Calendar.DAY_OF_MONTH
                )
            } ${calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())}"
        )
    }


    private fun calculateTodayAmount(expenses: List<UserExpense>) : Int {
        var sum = 0
        expenses.forEach {
            val calendar = Calendar.getInstance().apply { timeInMillis = it.datetime.toLong() }
            if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                sum += it.amount
            }
        }
        return sum
    }

    private fun calculateWeekAmount(expenses: List<UserExpense>) : Int {
        var sum = 0
        expenses.forEach {
            val calendar = Calendar.getInstance().apply { timeInMillis = it.datetime.toLong() }
            if (calendar.get(Calendar.WEEK_OF_YEAR) == Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) {
                sum += it.amount
            }
        }
        return sum
    }

    private fun calculateMonthAmount(expenses: List<UserExpense>) : Int {
        var sum = 0
        expenses.forEach {
            val calendar = Calendar.getInstance().apply { timeInMillis = it.datetime.toLong() }
            if (calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) {
                sum += it.amount
            }
        }
        return sum
    }


}