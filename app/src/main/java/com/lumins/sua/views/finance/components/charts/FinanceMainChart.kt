package com.lumins.sua.views.finance.components.charts


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.LineChartData


@Composable
fun FinanceMainChart(
    modifier: Modifier = Modifier, lineChartData: LineChartData?
) {


    if (lineChartData.isNotNull()) {
        LineChart(
            modifier = modifier.background(MaterialTheme.colorScheme.surface),

            lineChartData = lineChartData!!
        )
    } else {
        Box(modifier = modifier.size(300.dp), contentAlignment = Alignment.Center) {
            Text(
                text = "No expenses to show\ntry adding some expenses to see the chart",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }


    }


}
