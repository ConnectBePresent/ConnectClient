package com.example.connectcompose

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Utils {

    companion object {
        fun getDate(): String {
            return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            .format(Calendar.getInstance().time)
        }
    }
}