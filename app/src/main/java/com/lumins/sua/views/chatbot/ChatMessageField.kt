package com.lumins.sua.views.chatbot

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessageField(
    modifier: Modifier = Modifier,
    onSentPressed: (String) -> Unit,
) {
    var message by remember { mutableStateOf("") }


    TextField(
        modifier = modifier,
        value = message,
        onValueChange = { message = it },
        label = { Text("Type a message") },
        trailingIcon = {
            IconButton(onClick = { onSentPressed(message); message = "" }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.Send, contentDescription = "Send")
            }
        }
    )
}


@Preview
@Composable
fun ChatMessageFieldPreview() {
    MaterialTheme {
        ChatMessageField(Modifier.fillMaxWidth()) {}
    }
}