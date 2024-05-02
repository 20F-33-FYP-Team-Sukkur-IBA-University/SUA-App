package com.lumins.sua.views.finance.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.lumins.sua.views.finance.ExpenseCategory

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    entries: State<List<ExpenseCategory>>,
    strokeWidth: Float = 80f,
    selectedStrokeWidth: Float = 100f,
    gapAngle: Float = 2f,  // the gap angle,
    title: String,
    selectedCategory: String
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            var startAngle = 0f
            entries.value.toList().forEach { entry ->
                val sweepAngle = entry.percentage * 360f - gapAngle
                drawArc(
                    color = entry.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = if (entry.title == selectedCategory) selectedStrokeWidth else strokeWidth)
                )
                startAngle += sweepAngle + gapAngle
            }
        }
        Text(title, textAlign = TextAlign.Center, fontWeight = FontWeight.Normal, fontSize = 18.sp)
    }
}