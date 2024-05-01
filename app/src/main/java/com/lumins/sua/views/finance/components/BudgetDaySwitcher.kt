package com.lumins.sua.views.finance.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BudgetDaySwitcher(
    modifier: Modifier = Modifier,
//    onLeftArrowClicked: () -> Unit,
//    onRightArrowClicked: () -> Unit,
//    onDateClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "go left",
                modifier = Modifier.size(36.dp)
            )
        }
        Text(
            text = "12 Feb 2024",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black.copy(alpha = 0.6f),
            modifier = Modifier.clickable { /* TODO */  }
        )
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "go right",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}