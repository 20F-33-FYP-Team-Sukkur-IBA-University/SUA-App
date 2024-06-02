package com.lumins.sua.views.chatbot;

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.data.local.db.ChatMessage
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.repo.SuaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatBotViewModel(context: Context) : ViewModel() {
    private var repo = SuaRepository(DatabaseDriverFactory(context))

    private var _className = MutableStateFlow("BS-VIII(CS)-A")

    private var _isWaitingForResponse = MutableStateFlow(false)
    val isWaitingForResponse = _isWaitingForResponse.asStateFlow()

    private var _messages = MutableStateFlow(listOf<ChatMessage>())
    val messages = _messages.asStateFlow()

    init {
        viewModelScope.launch {
            _messages.value = repo.getDbChatMessages()
        }
    }

    fun sendMessageToAI(message: String) {
        _isWaitingForResponse.value = true
        viewModelScope.launch {
            repo.insertChatMessage(ChatMessage(0, message = message, role = "user"))
            _messages.value = _messages.value.plus(ChatMessage(0, message = message, role = "user"))
            val res = repo.sendChatMessage(
                _className.value,
                _messages.value.plus(ChatMessage(0, message, "user"))
            )
            if (res) {
                _messages.value = repo.getDbChatMessages()
                _isWaitingForResponse.value = false
            }
        }
    }

    fun clearConversation() {
        viewModelScope.launch {
            repo.clearChatMessages()
            _messages.value = listOf()
        }
    }

}

