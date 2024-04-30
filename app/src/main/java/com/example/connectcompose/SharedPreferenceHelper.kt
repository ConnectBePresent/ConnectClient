package com.example.connectcompose

import android.content.Context

class SharedPreferenceHelper {
    companion object {
        fun set(context: Context, key: String, userName: String) {
            context.getSharedPreferences(
                Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE
            ).edit().putString(key, userName).apply()
        }

        fun get(context: Context, key: String): String {
            return context.getSharedPreferences(
                Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE
            ).getString(key, "") ?: ""
        }

        fun clearAll(context: Context) {
            context.getSharedPreferences(
                Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE
            ).edit().clear().apply()
        }
    }
}