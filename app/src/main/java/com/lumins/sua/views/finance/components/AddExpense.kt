package com.lumins.sua.views.finance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumins.sua.data.local.db.UserExpense
import com.lumins.sua.data.model.ExpenseType
import com.lumins.sua.ui.theme.SUATheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddExpense(onAdd: (UserExpense) -> Unit) {
    var expenseName by remember { mutableStateOf("") }
    var amountString by remember { mutableStateOf("0") }
    var expenseTypeIndex by remember { mutableIntStateOf(0) }
    var isValidAmount by remember { mutableStateOf(true) }
    val categories = ExpenseType.entries.map { it.name.toCapitalized() }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add Expense", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = expenseName,
            onValueChange = { expenseName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
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
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Category")
        Spacer(modifier = Modifier.height(4.dp))
        FlowRow(Modifier.fillMaxWidth(), Arrangement.spacedBy(4.dp)) {
            categories.forEachIndexed { index, category ->
                FilterChip(
                    onClick = { expenseTypeIndex = index },
                    label = { Text(text = category) },
                    selected = index == expenseTypeIndex
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            enabled = isValidAmount,
            modifier = Modifier.align(Alignment.End),
            onClick = {
                val expense = UserExpense(
                    id = -1,
                    name = expenseName,
                    amount = amountString.toInt(),
                    expense_type = ExpenseType.entries[expenseTypeIndex],
                    datetime = System.currentTimeMillis().toString()
                )
                onAdd(expense)
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

private fun String.toCapitalized(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddExpense() {
    SUATheme {
        AddExpense(onAdd = {})
    }
}
