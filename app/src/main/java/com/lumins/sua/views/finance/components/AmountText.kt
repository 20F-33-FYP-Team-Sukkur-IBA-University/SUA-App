package com.lumins.sua.views.finance.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lumins.sua.android.utils.formatWithCommas

@Composable
fun AmountText(
    text: String,
    amount: Int
) {
    Column {
        Text(
            text = text,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = Color.Black.copy(alpha = 0.5f)
        )
        Text(text = amount.formatWithCommas(), fontSize = 22.sp, )
    }
}