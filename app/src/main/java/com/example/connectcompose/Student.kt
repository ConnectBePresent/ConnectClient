package com.example.connectcompose

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val rollNumber: Int,
    val name: String,
    val phoneNumber : String,
)