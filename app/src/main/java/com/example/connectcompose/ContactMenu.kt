package com.example.connectcompose


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ImportMSG(state: ContactState,
              onEvent: (ContactEvent)-> Unit){






}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Report(state: ContactState,
           onEvent: (ContactEvent)-> Unit){

    val dateState = rememberDatePickerState()
    if (state.isReportOpen) {
        DatePickerDialog(onDismissRequest = {  },
            confirmButton = {
                TextButton(onClick = { onEvent(ContactEvent.HideReport) }) {
                    Text(text = "Save")
                }
            }) {
            DatePicker(state = dateState)
        }
    }




}





@Composable
fun Message(state: ContactState,
            onEvent: (ContactEvent)-> Unit){




    Dialog(onDismissRequest = { onEvent(ContactEvent.HideMessage) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(Color.Black)
                .padding(10.dp),

            shape = RoundedCornerShape(16.dp)

        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                TextField(value = state.message, onValueChange = {
                    onEvent(ContactEvent.SetMessage(it))
                })
                TextButton(onClick = {
                    onEvent(ContactEvent.HideMessage)
                }) {
                    Text(text = "Save")
                }
            }


        }
    }













}