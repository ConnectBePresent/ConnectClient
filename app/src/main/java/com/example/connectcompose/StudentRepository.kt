package com.example.connectcompose

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class StudentRepository(private val studentDao: StudentDao) {

    private val studentList: LiveData<List<Student>> = studentDao.getAll()

    @WorkerThread
    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }

    @WorkerThread
    suspend fun delete(student: Student) {
        studentDao.delete(student)
    }

    @WorkerThread
    suspend fun update(student: Student) {
        studentDao.update(student)
    }

    fun getAll(): LiveData<List<Student>> {
        return studentList
    }
}