package com.lumins.sua.views.finance.components.charts


import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.android.views.finance.ExpenseCategory
import com.lumins.sua.ui.theme.SuaColors
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer


@Composable
fun FinanceMainChart(
    modifier: Modifier = Modifier,
    chartEntryModelProducer: ChartEntryModelProducer,
    expenseCategories: List<ExpenseCategory>
) {
    val legend = rememberLegend(expenseCategories = expenseCategories)
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultColumns = currentChartStyle.columnChart.columns
        Chart(
            modifier = modifier,
            chart = columnChart(
                columns = remember(defaultColumns) {
                    defaultColumns.map { defaultColumn ->
                        LineComponent(
                            defaultColumn.color,
                            defaultColumn.thicknessDp,
                            Shapes.pillShape,
                        )
                    }
                },
                mergeMode = ColumnChart.MergeMode.Grouped,
            ),
            chartModelProducer = chartEntryModelProducer,
            startAxis = rememberStartAxis(valueFormatter = startAxisValueFormatter),
            bottomAxis = rememberBottomAxis(valueFormatter = bottomAxisValueFormatter),
            marker = rememberMarker(),
            runInitialAnimation = true,
            legend = legend
        )
    }
}

@Composable
private fun rememberLegend(expenseCategories: List<ExpenseCategory>) = horizontalLegend(
    items = expenseCategories.map { expenseCategory ->
        legendItem(
            icon = shapeComponent(Shapes.pillShape, expenseCategory.color),
            label = textComponent(
                color = currentChartStyle.axis.axisLabelColor,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = expenseCategory.title,
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

private val color1 = SuaColors.darkRedMisc
private val color2 = SuaColors.lightGreenMisc
private val color3 = SuaColors.darkGrayMisc
private val color4 = SuaColors.primaryBlue
private val chartColors = listOf(color1, color2, color3, color4)
private val weeksOfMonth = listOf("Week 1", "Week 2", "Week 3", "Week 4", "Week 5")
private val bottomAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _ -> weeksOfMonth[x.toInt() % weeksOfMonth.size] }
private val startAxisValueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> {y, _ -> y.toInt().toString()}


private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)