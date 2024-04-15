package com.example.connectcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalList(
    onEvent: (ContactEvent) -> Unit,
    state: ContactState
){

    Scaffold(
        topBar = {
                 TopAppBar(title = { Text(text = "Absent Students",
                     fontSize = 20.sp, modifier = Modifier.fillMaxWidth(1f)
                         ) })

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {onEvent(ContactEvent.SendMessage)
                for(i in state.absent){
                    onEvent(ContactEvent.DeleteFinalList(i))
                }


            }) {
                Text(text = "Send")

            }


        }
    ) {padding->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){


            items(state.absent){contact->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                    Column(modifier = Modifier
                        .weight(1f)) {

                        Text(contact.firstName,fontSize = 20.sp)
                        Text(contact.phoneNumber,fontSize = 20.sp)
                    }
                    IconButton(onClick = {
                             onEvent(ContactEvent.DeleteFinalList(contact))

                    }) {
                        Icon(imageVector = Icons.Default.Delete
                            ,contentDescription = "Delete Contact")

                    }
                }
            }



        }


    }




}