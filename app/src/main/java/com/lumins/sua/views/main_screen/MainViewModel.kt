package com.lumins.sua.views.main_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.navigation.SuaScreen
import com.lumins.sua.utils.DataStoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {
    private val prefs = DataStoreUtils

    private var _startDestination = MutableStateFlow(SuaScreen.Loading.route)
    val startDestination = _startDestination.asStateFlow()


    init {
        viewModelScope.launch {
            prefs.getIsFirstTime(context).collectLatest { isFirstTime ->
                _startDestination.value = if (isFirstTime) {
                    SuaScreen.Onboarding.route
                } else {
                    SuaScreen.Timetable.route
                }
            }
        }
    }

}
