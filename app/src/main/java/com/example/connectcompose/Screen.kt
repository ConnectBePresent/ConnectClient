package com.example.connectcompose

sealed class Screen(val route : String) {
    object Welcome : Screen("welcome")
    object StudentList : Screen("studentList")
    object Login : Screen("login")
    object StudentEntry : Screen("studentEntry")
    object StudentReport : Screen("studentReport")
    object MessageFrag : Screen("messageFrag")
    object ReportFrag : Screen("reportFrag")
    object FinalList : Screen("finalList")
    object LoginPage2 : Screen("loginPage2")
}