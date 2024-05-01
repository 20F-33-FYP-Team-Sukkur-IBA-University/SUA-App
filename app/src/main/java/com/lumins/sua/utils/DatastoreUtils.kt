package com.lumins.sua.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreUtils {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fyp-sua-ds")

    suspend fun saveClassName(context: Context, className: String) {
        context.dataStore.edit { preferences ->
            preferences[CLASS_NAME_KEY] = className
        }
    }

    // get string as value not flow
    suspend fun getClassName(context: Context, defaultValue: String): String {
        return context.dataStore.data.first()[CLASS_NAME_KEY] ?: defaultValue
    }

    // save boolean value
    suspend fun saveIsFirstTime(context: Context, isFirstTime: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME] = isFirstTime
        }
    }

    // get boolean value as flow
    fun getIsFirstTime(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_FIRST_TIME] ?: true
        }
    }

    private val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
    private val CLASS_NAME_KEY = stringPreferencesKey("class_name_key")

}