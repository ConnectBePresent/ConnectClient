package com.example.connectcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
   onEvent: (ContactEvent)-> Unit,
    state: ContactState
){

    //mark







    //mark
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route ){
         composable(route = Screen.Login.route ){
             Loginpage1(modifier = Modifier,navController = navController)
         }
        composable(route = Screen.Welcome.route){
            Welcome(modifier = Modifier,navController = navController)
        }
        composable(route = Screen.StudentEntry.route){
            ContactScreen(state = state, onEvent = onEvent,navController = navController)
        }
        composable(route = Screen.StudentReport.route){
            StudentReport()
        }
        composable(route = Screen.MessageFrag.route){
            MessageFrag(onEvent = onEvent, state = state, navController = navController)
        }
        composable(route = Screen.ReportFrag.route){
            ReportFrag(onEvent = onEvent, state = state, navController = navController)
        }
        composable(route = Screen.FinalList.route){
            FinalList(onEvent= onEvent,state = state)
        }




    }



}