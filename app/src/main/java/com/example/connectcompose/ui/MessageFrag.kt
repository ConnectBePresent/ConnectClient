package com.example.connectcompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connectcompose.ContactEvent
import com.example.connectcompose.ContactState
import com.example.connectcompose.Screen

@Composable
fun MessageFrag(
    onEvent : (ContactEvent) -> Unit,
    state : ContactState,
    navController : NavController
){

    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Spacer(Modifier
            .weight(1f))
        Row {
             Text(text = "Type your\n\n\n\nmessage here",
                 fontSize = 40.sp,
                 color = Color.White,

                 letterSpacing = 8.sp)
        }
        Row {
                 TextField(value = state.message ,
                      onValueChange =
                     {

                         onEvent(ContactEvent.SetMessage(it))

                     },modifier = Modifier
                         .fillMaxWidth(1f))

        }
        Spacer(modifier = Modifier
            .weight(1f))
        Row (
             modifier = Modifier
                 .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.End
        ){
            Button(onClick = {

                navController.navigate(Screen.StudentEntry.route)

            },
                modifier = Modifier
                    .padding(horizontal = 10.dp)) {
                Text("Save")
            }
            Button(onClick = {

                onEvent(ContactEvent.SetMessage(""))
                navController.navigate(Screen.StudentEntry.route)

            }) {
                Text("Cancel")
            }
        }



    }




}