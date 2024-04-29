package com.example.connectcompose

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class AttendanceEntry(
    @PrimaryKey
    val date: String,
    @TypeConverters(Converter::class)
    val absenteeList: List<Student>
)
