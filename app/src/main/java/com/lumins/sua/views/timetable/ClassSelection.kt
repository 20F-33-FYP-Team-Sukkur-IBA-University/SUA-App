package com.lumins.sua.views.timetable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ClassSelection(
    modifier: Modifier = Modifier,
    classNames: List<String>,
    onSelection: (String) -> Unit
) {
    var searchValue by remember { mutableStateOf("") }
    var filteredClassNames by remember { mutableStateOf(classNames) }
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        TextField(
            value = searchValue,
            placeholder = { Text("Search for a class. e.g, CS VIII") },
            onValueChange = {
                searchValue = it
                val parts = it.split(" ")
                filteredClassNames = classNames.filter { className ->
                    parts.all { part -> className.contains(part, ignoreCase = true) }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredClassNames) { className ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.primaryContainer,
                        contentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer)
                    ),
                    onClick = { onSelection(className) },
                ) {
                    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                        Text(
                            text = className,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun ClassSelectionPreview() {
    ClassSelection(
        classNames = listOf("CS VIII", "CS IX", "CS X", "CS XI", "CS XII"),
        onSelection = {}
    )
}