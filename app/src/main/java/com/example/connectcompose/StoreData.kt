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
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore")
        private val INDIVIDUAL_USER_NAME = stringPreferencesKey("individual_user_name")
    }

    val getIndividualUserName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[INDIVIDUAL_USER_NAME] ?: ""
    }

    val isIndividualUserNameStored: Flow<Boolean> = context.dataStore.data.map { preference ->
        preference.contains(INDIVIDUAL_USER_NAME)
    }

    suspend fun setIndividualUserName(token: String) {
        context.dataStore.edit { preferences ->
            preferences[INDIVIDUAL_USER_NAME] = token
        }
    }
}