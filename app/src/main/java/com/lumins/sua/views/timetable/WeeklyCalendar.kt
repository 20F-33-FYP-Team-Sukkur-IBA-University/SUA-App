package com.lumins.sua.views.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.lumins.sua.ui.theme.SuaColors
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeeklyCalendar(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onDateSelection: (LocalDate) -> Unit
) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = daysOfWeek()

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    WeekCalendar(
        modifier = modifier,
        state = state,
        dayContent = { day ->
            WeeklyDay(
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f) // This is important for square sizing!
                    .align(Alignment.Center),
                day = day,
                selected = selectedDate == day.date
            ) {
                onDateSelection(it.date)
            }
        },
        weekHeader = { DaysOfWeekTitle(daysOfWeek = daysOfWeek) }
    )
}

@Composable
fun WeeklyDay(
    modifier: Modifier = Modifier,
    day: WeekDay,
    selected: Boolean,
    onClick: (WeekDay) -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .clip(CircleShape)
                .background(if (selected) SuaColors.primaryBlueVariant else Color.Transparent)
                .clickable { onClick(day) },
            Alignment.Center,
        ) {
            Text(
                text = (day.date.dayOfMonth.toString()),
                style = MaterialTheme.typography.titleMedium,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = SuaColors.primaryBlueVariant
            )
        }
    }
}
