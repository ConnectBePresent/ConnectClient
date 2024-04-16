package com.example.connectcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Welcome(
    modifier: Modifier, navController: NavController
) {
    Surface(Modifier.fillMaxSize()) {

        val name = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            Text(
                "Welcome to Connect!",
                Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "What should we call you?",
                Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                shape = RoundedCornerShape(16.dp),
                label = { Text("Name") },
                value = name.value,
                onValueChange = {
                    name.value = it
                },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            Button(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp), onClick = {
                navController.navigate(route = Screen.StudentEntry.route)
            }) { Text("Next") }
        }
    }
}