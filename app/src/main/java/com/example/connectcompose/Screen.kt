package com.example.connectcompose

sealed class Screen(val route : String) {
    object Welcome : Screen("welcome")
    object StudentList : Screen("studentList")
    object Login : Screen("login")
    object StudentEntry : Screen("studentEntry")
    object StudentReport : Screen("studentReport")
}