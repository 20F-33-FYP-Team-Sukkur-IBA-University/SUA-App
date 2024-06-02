package com.lumins.sua.views.email_alerts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.data.local.db.EmailAlert
import com.lumins.sua.repo.SuaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailAlertViewModel(context: Context): ViewModel() {
    private val repo = SuaRepository(DatabaseDriverFactory(context))

    private val _emailAlerts = MutableStateFlow<List<EmailAlert>>(emptyList())
    val emailAlerts = _emailAlerts.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            _emailAlerts.value = repo.getEmailAlerts(forcedReload = true)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _emailAlerts.value = repo.getEmailAlerts(forcedReload = true)
            _isRefreshing.value = false
        }

    }

    fun deleteEmailAlert(emailAlert: EmailAlert) {
        viewModelScope.launch {
            repo.deleteEmailAlert(emailAlert)
            _emailAlerts.value = repo.getEmailAlerts(forcedReload = false)
        }
    }
}