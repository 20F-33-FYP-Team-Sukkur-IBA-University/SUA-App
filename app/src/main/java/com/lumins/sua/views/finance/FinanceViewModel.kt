package com.lumins.sua.views.finance

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.R
import com.lumins.sua.android.views.finance.ExpenseCategory
import com.lumins.sua.ui.theme.SuaColors
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.util.RandomEntriesGenerator
import kotlinx.coroutines.launch

class FinanceViewModel: ViewModel() {

    val expenseCategories = mutableStateListOf(
        ExpenseCategory("Food", SuaColors.darkRedMisc, 0.15f, R.drawable.icon_food),
        ExpenseCategory("Shopping", SuaColors.lightGreenMisc, 0.45f, R.drawable.icon_cart),
        ExpenseCategory("Bitcoin", SuaColors.primaryBlue, 0.20f, R.drawable.icon_bitcoin),
        ExpenseCategory("Transport", SuaColors.primaryBlueVariant, 0.20f, R.drawable.icon_transport),
    )
    private val generator = RandomEntriesGenerator(
        xRange = 0..GENERATOR_X_RANGE_TOP,
        yRange = GENERATOR_Y_RANGE_BOTTOM..GENERATOR_Y_RANGE_TOP,
    )



    internal val multiDataSetChartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer()

    init {
        viewModelScope.launch {
            val randomDataSet = List(MULTI_ENTRIES_COMBINED) { generator.generateRandomEntries() }
            multiDataSetChartEntryModelProducer.setEntries(randomDataSet)
        }
    }


    private companion object {
        const val MULTI_ENTRIES_COMBINED = 4
        const val GENERATOR_X_RANGE_TOP = 96
        const val GENERATOR_Y_RANGE_BOTTOM = 50
        const val GENERATOR_Y_RANGE_TOP = 301
    }
}