package com.lumins.sua.views.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.lumins.sua.ui.theme.SuaColors
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthlyCalendar(
    modifier: Modifier,
    selectedDate: LocalDate,
    onMonthScroll: (LocalDate) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = daysOfWeek()
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    LaunchedEffect(state.firstVisibleMonth) {
        onMonthScroll(state.firstVisibleMonth.yearMonth.atDay(1))
    }

    HorizontalCalendar(
        state = state,
        dayContent = { day ->
            MonthlyDay(
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f) // This is important for square sizing!
                    .align(Alignment.Center),
                day = day,
                selected = selectedDate == day.date
            ) { onDateSelected(it) }
        },
        monthHeader = { DaysOfWeekTitle(daysOfWeek = daysOfWeek) }
    )
}


@Composable
fun MonthlyDay(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    selected: Boolean,
    onClick: (LocalDate) -> Unit
) {
    val alpha = if (day.position == DayPosition.MonthDate) 1.0f else 0.5f
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .clip(CircleShape)
                .background(
                    if (selected) SuaColors.primaryBlueVariant else (
                            if (day.date.dayOfWeek.getDisplayName(
                                    TextStyle.FULL,
                                    Locale.getDefault()
                                ) == "Sunday" || day.date.dayOfWeek.getDisplayName(
                                    TextStyle.FULL,
                                    Locale.getDefault()
                                ) == "Saturday"
                            ) Color.Red.copy(
                                alpha = 0.2f
                            ) else Color.Transparent)
                )
                .clickable(
                    enabled = day.position == DayPosition.MonthDate,
                    onClick = { onClick(day.date) }
                ),
            Alignment.Center,
        ) {
            Text(
                text = (day.date.dayOfMonth.toString()),
                style = MaterialTheme.typography.titleMedium,
                color = if (selected) Color.White.copy(alpha)
                else MaterialTheme.colorScheme.onBackground.copy(
                    alpha
                )
            )
        }
    }
}
