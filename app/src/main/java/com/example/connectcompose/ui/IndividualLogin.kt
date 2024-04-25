package com.example.connectcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connectcompose.Screen
import com.example.connectcompose.StoreData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun IndividualLogin(
    navController: NavController
) {
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

                Button(modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .align(Alignment.End),
                    onClick = {
                        if (name.isNotEmpty()) {

                            GlobalScope.launch {
                                StoreData(context = navController.context).setUserName(name)
                            }

                            navController.navigate(route = Screen.StudentEntry.route)
                        } else {
                            name = "Guest"
                        }
                    }) { Text("Next") }
            }
        }
    }
}