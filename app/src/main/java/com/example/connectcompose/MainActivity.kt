package com.example.connectcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectcompose.application.Application
import com.example.connectcompose.ui.IndividualLogin
import com.example.connectcompose.ui.InstituteLogin
import com.example.connectcompose.ui.StudentDetails
import com.example.connectcompose.ui.Welcome
import com.example.connectcompose.ui.theme.ConnectComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as Application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var startDestination = Constants.SCREEN_WELCOME

        val userName = SharedPreferenceHelper.get(
            this@MainActivity,
            Constants.INDIVIDUAL_USER_NAME
        )

        val classEmail = SharedPreferenceHelper.get(
            this@MainActivity,
            Constants.INSTITUTE_EMAIL
        )

        if (userName.isNotEmpty() || classEmail.isNotEmpty())
            startDestination = Constants.SCREEN_STUDENT_DETAILS

        firebaseAuth = Firebase.auth
        firebaseDatabase = Firebase.database

        setContent {
            ConnectComposeTheme {

                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(route = Constants.SCREEN_WELCOME) {
                        Welcome(navController)
                    }

                    composable(route = Constants.SCREEN_INDIVIDUAL_LOGIN) {
                        IndividualLogin(navController)
                    }
                    composable(route = Constants.SCREEN_INSTITUTE_LOGIN) {
                        InstituteLogin(navController, viewModel, firebaseAuth, firebaseDatabase)
                    }

                    composable(route = Constants.SCREEN_STUDENT_DETAILS) {
                        StudentDetails(navController, viewModel, firebaseDatabase)
                    }
                }

            }
        }
    }
}

