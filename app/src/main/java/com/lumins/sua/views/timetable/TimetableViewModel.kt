package com.lumins.sua.views.timetable

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumins.sua.data.local.db.DatabaseDriverFactory
import com.lumins.sua.data.local.db.Timetable
import com.lumins.sua.repo.SuaRepository
import com.lumins.sua.utils.DataStoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimetableViewModel(context: Context) : ViewModel() {
    private val repo = SuaRepository(DatabaseDriverFactory(context))
    private val prefs = DataStoreUtils

    private var _timetables = MutableStateFlow(listOf<Timetable>())
    val timetables = _timetables.asStateFlow()

    private var _classNames = MutableStateFlow(listOf<String>())
    val classNames = _classNames.asStateFlow()

    private var _selectedClassName = MutableStateFlow("empty")
    val selectedClassName = _selectedClassName.asStateFlow()

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedClassName.value = prefs.getClassName(context, "empty")
            _timetables.value =
                repo.getClassTimetableByName(
                    false,
                    _selectedClassName.value
                ) + repo.getStarredTimetables()
            _classNames.value = repo.getClassNames(false)

        }

    }


    fun onEvent(event: TimetableEvents) {
        when (event) {
            is TimetableEvents.TimetableReload -> {
                viewModelScope.launch {
                    _timetables.value = getUpdatedTimetables(_selectedClassName.value)
                    _classNames.value = repo.getClassNames(false)
                }
            }

            is TimetableEvents.SelectedClassName -> {
                viewModelScope.launch {
                    _selectedClassName.value = event.className
                    _timetables.value = getUpdatedTimetables(event.className)
                }
            }

            is TimetableEvents.StarredToggled -> {
                viewModelScope.launch {
                    Log.d("TimetableViewModel", "onEvent: ${event.timetable}")
                    repo.updateTimetable(event.timetable)
                    _timetables.value = getUpdatedTimetables(_selectedClassName.value)
                }

            }

            is TimetableEvents.RefreshTimetable -> {
                Log.d("RefreshTimetable", "onEvent:start ")
                viewModelScope.launch {
                    Log.d("RefreshTimetable", "onEvent:start ")
                    _isRefreshing.value = true
                    try {
                        _timetables.value = emptyList()
                        _timetables.value = getUpdatedTimetables(_selectedClassName.value, true)

                        Log.d("RefreshTimetable", "onEvent:loading ")
                    } finally {
                        _isRefreshing.value = false

                        Log.d("RefreshTimetable", "onEvent:finished ")
                    }
                }
            }
        }
    }

    fun refreshClassData(context: Context) {
        viewModelScope.launch {
            val savedClassName = prefs.getClassName(context, "empty")
//            if (savedClassName != "empty" && savedClassName != _selectedClassName.value) {
//            }
            _selectedClassName.value = savedClassName
            _timetables.value = getUpdatedTimetables(savedClassName)
        }
    }

    private suspend fun getUpdatedTimetables(
        className: String,
        forceReload: Boolean = false
    ): List<Timetable> {
        if (className == "empty") return emptyList()

        val allTimetables = repo.getClassTimetableByName(
            forceReload,
            className
        ) + repo.getStarredTimetables()
        Log.d(
            "TimetableViewModel",
            "getUpdatedTimetables: $allTimetables"
        )
        return allTimetables.distinctBy { it.id }
    }

}

// Timetable Events
sealed class TimetableEvents {
    data object TimetableReload : TimetableEvents()
    data class SelectedClassName(val className: String) : TimetableEvents()
    data class StarredToggled(val timetable: Timetable) : TimetableEvents()
    data object RefreshTimetable : TimetableEvents()
}
