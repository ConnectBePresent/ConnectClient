package com.example.connectcompose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connectcompose.Constants
import com.example.connectcompose.SharedPreferenceHelper

@Composable
fun IndividualLogin(navController: NavController) {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {

            var name by remember { mutableStateOf("Guest") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
            ) {
                Text(
                    text = "Hi,\n\n\nWelcome to\n\n\nConnect!",
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                    fontSize = 56.sp,
                    minLines = 9,
                    maxLines = 9,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Text(
                    text = "What should we call you?",
                    Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp),
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Name") },
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )

                Spacer(modifier = Modifier.weight(1f))

                TextButton(modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .align(Alignment.End),
                    border = BorderStroke(
                        1.dp, MaterialTheme.colorScheme.onSurface
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(
                            0xFF292D32
                        )
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
                    onClick = {
                        if (name.isNotEmpty()) {

                            SharedPreferenceHelper.set(
                                navController.context,
                                Constants.INDIVIDUAL_USER_NAME,
                                name
                            )

                            SharedPreferenceHelper.set(
                                navController.context,
                                Constants.USER_MODE,
                                Constants.INDIVIDUAL_MODE
                            )

                            navController.navigate(Constants.SCREEN_STUDENT_DETAILS) {
                                popUpTo(Constants.SCREEN_INDIVIDUAL_LOGIN) { inclusive = true }
                            }
                        } else {
                            name = "Guest"
                        }
                    }) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                append("Next")
                            }
                        }, fontSize = 16.sp, fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}