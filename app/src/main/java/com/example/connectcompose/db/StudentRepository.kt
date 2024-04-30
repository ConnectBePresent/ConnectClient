package com.example.connectcompose.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.connectcompose.AttendanceEntry
import com.example.connectcompose.Student

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

    fun getAllStudents(): LiveData<List<Student>> {
        return studentList
    }

    private val attendanceList: LiveData<List<AttendanceEntry>> = studentDao.getAllAttendanceEntries()

    @WorkerThread
    suspend fun insert(attendanceEntry: AttendanceEntry) {
        studentDao.insert(attendanceEntry)
    }

    @WorkerThread
    suspend fun delete(attendanceEntry: AttendanceEntry) {
        studentDao.delete(attendanceEntry)
    }

    @WorkerThread
    suspend fun update(attendanceEntry: AttendanceEntry) {
        studentDao.update(attendanceEntry)
    }

    @WorkerThread
    suspend fun getAttendanceEntry(date : String): AttendanceEntry? {
        return studentDao.getAttendanceEntry(date)
    }

    fun getAllAttendanceEntries(): LiveData<List<AttendanceEntry>> {
        return attendanceList
    }

    @WorkerThread
    suspend fun clearAll() {
        studentDao.clearAll()
    }
}