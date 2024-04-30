package com.example.connectcompose.ui


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connectcompose.Constants
import com.example.connectcompose.R
import com.example.connectcompose.SharedPreferenceHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InstituteLogin(navController: NavController, firebaseAuth: FirebaseAuth) {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .fillMaxWidth(0.75f),
                    text = "Login",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Medium
                )

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var passwordVisibility: Boolean by remember { mutableStateOf(false) }

                OutlinedTextField(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .fillMaxWidth(0.75f),
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text("Class Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = @Composable {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_email), "email icon"
                        )
                    })

                OutlinedTextField(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .fillMaxWidth(0.75f),
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = @Composable {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_password), "padlock icon"
                        )
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = @Composable {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            if (passwordVisibility) Icon(
                                ImageVector.vectorResource(R.drawable.ic_lock_open), ""
                            )
                            else Icon(ImageVector.vectorResource(R.drawable.ic_lock), "")
                        }
                    })

                var buttonText by remember { mutableStateOf("Login") }

                TextButton(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                    border = BorderStroke(
                        1.dp, MaterialTheme.colorScheme.onSurface
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(
                            0xFF292D32
                        )
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(12.dp),
                    onClick = {
                        GlobalScope.launch {

                            if (email.isBlank() || password.isBlank()) {

                                buttonText = "Fields Empty!!"
                                delay(1000)
                                buttonText = "Login"

                                return@launch
                            }

                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        buttonText = "Success!"

                                        Toast.makeText(
                                            navController.context,
                                            "Yayy!",
                                            Toast.LENGTH_SHORT,
                                        ).show()

                                        SharedPreferenceHelper.set(
                                            navController.context, Constants.INSTITUTE_EMAIL, email
                                        )
                                    } else {
                                        buttonText = "Something went wrong"
                                    }
                                }

                            delay(1000)
                            buttonText = "Login"
                        }
                    },
                    content = {
                        Text(
                            text = buttonText,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    })
            }
        }
    }
}

