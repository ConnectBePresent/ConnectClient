package com.example.connectcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportFrag(
    onEvent: (ContactEvent)-> Unit,
    state: ContactState,
    navController : NavController
){
  val timeState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    Column(modifier = Modifier
        .padding(10.dp)){

        Row {
            DatePicker(state = timeState)
        }
        Spacer(modifier = Modifier
            .weight(1f))
        Row(modifier = Modifier
            .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.End) {
            Button(onClick = { navController.navigate(Screen.StudentEntry.route) }) {
                 Text("Save")
            }
        }

    }





}