package com.example.connectcompose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.connectcompose.ContactEvent
import com.example.connectcompose.ContactState
import com.example.connectcompose.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportFrag(
    onEvent: (ContactEvent) -> Unit, state: ContactState, navController: NavController
) {
    MaterialTheme {
        Surface {

            val timeState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

            Column(modifier = Modifier.padding(32.dp)) {

                DatePicker(state = timeState)

                Spacer(modifier = Modifier.weight(1f))

                TextButton(modifier = Modifier.align(Alignment.End),
                    onClick = { navController.navigate(Screen.StudentEntry.route) }) {
                    Text("Save")
                }
            }
        }
    }
}