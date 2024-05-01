package com.lumins.sua.views.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.repo.SuaRepository
import com.lumins.sua.utils.DataStoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(context: Context) : ViewModel() {
    private val repo = SuaRepository(DatabaseDriverFactory(context))
    private val prefs = DataStoreUtils

    private var _classNames = MutableStateFlow(listOf<String>())
    val classNames = _classNames.asStateFlow()

    private var _selectedClassName = MutableStateFlow("empty")
    val selectedClassName = _selectedClassName.asStateFlow()

    init {
        viewModelScope.launch {
            _classNames.value = repo.getClassNames(false)
            _selectedClassName.value = prefs.getClassName(context, "empty")
        }
    }

    fun onClassSelected(context: Context, className: String) {
        viewModelScope.launch {
            prefs.saveClassName(context, className)
            _selectedClassName.value = prefs.getClassName(context, "empty")
        }
    }
}