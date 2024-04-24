package com.example.connectcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.connectcompose.ui.theme.ConnectComposeTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    private lateinit var auth : FirebaseAuth


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext, ContactDatabase::class.java, "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ContactViewModel(db.dao) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        setContent {
            ConnectComposeTheme {
                // A surface container using the 'background' color from the theme
                val state by viewModel.state.collectAsState()
                Navigation(state = state, onEvent = viewModel::onEvent,auth = auth)
            }
        }
    }
}

