package com.example.connectcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectcompose.application.Application
import com.example.connectcompose.ui.IndividualLogin
import com.example.connectcompose.ui.IndividualStudentListScreen
import com.example.connectcompose.ui.InstituteLogin
import com.example.connectcompose.ui.Welcome
import com.example.connectcompose.ui.theme.ConnectComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as Application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = Firebase.auth

        setContent {
            ConnectComposeTheme {

                var startDestination by remember { mutableStateOf(Constants.SCREEN_WELCOME) }

                LaunchedEffect(Unit) {

                    val userName = SharedPreferenceHelper.get(
                        this@MainActivity,
                        Constants.INDIVIDUAL_USER_NAME
                    )

                    if (userName.isNotEmpty())
                        startDestination = Constants.SCREEN_INDIVIDUAL_STUDENT_LIST

                    val classEmail = SharedPreferenceHelper.get(
                        this@MainActivity,
                        Constants.INSTITUTE_EMAIL
                    )

                    Log.e("vishnu", "hi: $classEmail")
                }

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
                        InstituteLogin(navController, firebaseAuth)
                    }

                    composable(route = Constants.SCREEN_INDIVIDUAL_STUDENT_LIST) {
                        IndividualStudentListScreen(navController, viewModel)
                    }
                }

            }
        }
    }
}

