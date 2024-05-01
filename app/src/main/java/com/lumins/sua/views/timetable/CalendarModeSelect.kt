package com.lumins.sua.views.timetable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.ui.theme.SuaColors

@Composable
fun CalendarModeSelect(
    onSelection: (Int) -> Unit
) {
    // Timetable calendar mode selection
    var selectedIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .background(SuaColors.primaryBlue, RoundedCornerShape(28.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = selectedIndex == 1) {
            ClickableText(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 14.sp,
                            color = SuaColors.darkGrayMisc,
                            fontWeight = FontWeight.Normal,
                        )
                    ) { append("Weekly") }
                },
                onClick = { selectedIndex = 0; onSelection(0) }
            )
        }

        AnimatedVisibility(visible = selectedIndex  == 0) {
            Text(text = "Weekly", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = SuaColors.onPrimary)
        }

        Box(
            modifier = Modifier
                .height(28.dp)
                .width(3.dp)
                .background(Color.White, RoundedCornerShape(24.dp))
        )

        AnimatedVisibility(visible = selectedIndex == 0) {
            ClickableText(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 14.sp,
                            color = SuaColors.darkGrayMisc,
                            fontWeight = FontWeight.Normal,
                        )
                    ) { append("Monthly") }
                },
                onClick = { selectedIndex = 1; onSelection(1) }
            )
        }

        AnimatedVisibility(visible = selectedIndex  == 1) {
            Text(text = "Monthly", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SuaColors.onPrimary)
        }

    }
}