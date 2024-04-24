package com.example.connectcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation(
    onEvent: (ContactEvent) -> Unit, state: ContactState,auth : FirebaseAuth
) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginPage(modifier = Modifier, navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            Welcome(modifier = Modifier, navController = navController)
        }
        composable(route = Screen.StudentEntry.route) {
            ContactScreen(state = state, onEvent = onEvent, navController = navController)
        }
        composable(route = Screen.StudentReport.route) {
            StudentReport()
        }
        composable(route = Screen.MessageFrag.route) {
            MessageFrag(onEvent = onEvent, state = state, navController = navController)
        }
        composable(route = Screen.ReportFrag.route) {
            ReportFrag(onEvent = onEvent, state = state, navController = navController)
        }
        composable(route = Screen.FinalList.route) {
            FinalList(onEvent = onEvent, state = state)
        }
        composable(route = Screen.LoginPage2.route){
            Loginpage2(modifier = Modifier,auth = auth)
        }
    }
}