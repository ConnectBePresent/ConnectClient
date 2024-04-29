package com.example.connectcompose.application

import android.app.Application
import com.example.connectcompose.StudentDatabase
import com.example.connectcompose.StudentRepository

class Application : Application() {
    private val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}