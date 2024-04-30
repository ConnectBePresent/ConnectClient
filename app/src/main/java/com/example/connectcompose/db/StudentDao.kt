package com.example.connectcompose.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectcompose.AttendanceEntry
import com.example.connectcompose.Student

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * FROM Student order by rollNumber asc")
    fun getAll(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(attendanceEntry: AttendanceEntry)

    @Update
    fun update(attendanceEntry: AttendanceEntry)

    @Delete
    fun delete(attendanceEntry: AttendanceEntry)

    @Query("SELECT * FROM AttendanceEntry where date = :date")
    fun getAttendanceEntry(date: String): AttendanceEntry?

    @Query("SELECT * FROM AttendanceEntry order by date desc")
    fun getAllAttendanceEntries(): LiveData<List<AttendanceEntry>>
}