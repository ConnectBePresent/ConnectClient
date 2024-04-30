package com.example.connectcompose.application

import android.app.Application
import com.example.connectcompose.db.StudentDatabase
import com.example.connectcompose.db.StudentRepository

class Application : Application() {
    private val studentDatabase by lazy {
        StudentDatabase.getDatabase(
            this, "student_database"
        )
    }
    val repository by lazy { StudentRepository(studentDatabase.studentDao()) }
}