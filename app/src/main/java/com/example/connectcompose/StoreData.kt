package com.example.connectcompose

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userName")
        private val USER_NAME = stringPreferencesKey("user_name")
    }

    val getUserName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME] ?: ""
    }

    val isUserNameStored: Flow<Boolean> = context.dataStore.data.map { preference ->
        preference.contains(USER_NAME)
    }

    suspend fun setUserName(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = token
        }
    }
}