package com.example.connectcompose

data class Institute(
    val classList: List<ClassData>,
    val instituteId: String,
    val institutePassword: String
)
data class ClassData(
    val division: String,
    val standard: Int,
    val teacherEmail: String,
    val teacherPassword: String,
    val studentList: List<Student>?=null
)
data class Student(
    val name: String,
    val parentPhoneNumber: String,
    val rollNumber: Int
)