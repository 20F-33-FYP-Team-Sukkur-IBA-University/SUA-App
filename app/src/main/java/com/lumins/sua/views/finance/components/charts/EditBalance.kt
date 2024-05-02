package com.lumins.sua.views.finance.components.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditBalance(
    currentAmount: Int,
    onDone: (Int) -> Unit,
) {
    var amountString by remember { mutableStateOf("$currentAmount") }
    var isValidAmount by remember { mutableStateOf(true) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Edit Balance", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amountString,
            onValueChange = {
                amountString = it; isValidAmount =
                it.toIntOrNull() != null && it.toIntOrNull()!! > 0
            },
            label = { Text("Amount") },
            isError = !isValidAmount,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedButton(
            enabled = isValidAmount,
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onDone(amountString.toInt())
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Done")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "Done")
            }

        }
    }
}