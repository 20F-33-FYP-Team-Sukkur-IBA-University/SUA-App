package com.lumins.sua.views.chatbot

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.android.utils.viewModelFactory

@Composable
fun ChatBotScreen() {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val viewModel: ChatBotViewModel =
        viewModel(factory = viewModelFactory { ChatBotViewModel(context) })
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    var showConfirmationDialog by remember { mutableStateOf(false) }


    Scaffold(
        bottomBar = {
            ChatMessageField(Modifier.fillMaxWidth()) {
                focusManager.clearFocus()
                viewModel.sendMessageToAI(it)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showConfirmationDialog = true },
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete")
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AnimatedVisibility(visible = showConfirmationDialog) {
                AlertDialog(
                    modifier = Modifier.align(Alignment.Center),
                    onDismissRequest = { showConfirmationDialog = false },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.DeleteForever,
                            contentDescription = "delete"
                        )
                    },
                    title = { Text(text = "Delete Messages") },
                    text = { Text(text = "Are you sure you want to delete all messages?") },
                    dismissButton = { TextButton(onClick = { showConfirmationDialog = false}) { Text(text = "Cancel") } },
                    confirmButton = {
                        TextButton(onClick = { showConfirmationDialog = false; viewModel.clearConversation()}) {
                            Text(text = "Delete", color = Color.Red)
                        }
                    },
                )
            }

            LazyColumn(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    message.message?.let { msg ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            ChatMessage(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(if (message.role == "user") Alignment.CenterStart else Alignment.CenterEnd),
                                text = msg,
                                chatMessageShape = if (message.role == "user") {
                                    LeftAlignedChatMessageShape(
                                        tipSize = 4.dp,
                                        cornerRadius = 10.dp
                                    )
                                } else {
                                    RightAlignedChatMessageShape(
                                        tipSize = 4.dp,
                                        cornerRadius = 10.dp
                                    )
                                },
                                color = if (message.role == "user") {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.secondaryContainer
                                }
                            )

                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun ChatBotScreenPreview() {
    MaterialTheme {
        ChatBotScreen()
    }
}