package com.example.connectcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentReport(){
    Card {

        Column (
            modifier = Modifier
                .padding(10.dp)
            , verticalArrangement = Arrangement.spacedBy(10.dp)
        ){

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){

                Icon(imageVector = Icons.Default.ArrowBack,"Arrow back")

            }

            Row(){
                Column {
                    Text("Report",
                        fontSize = 30.sp)
                    Text(text = "Enter the Date")
                }

            }

            Row (){

                  TextField(value = "", onValueChange = {},
                      modifier = Modifier.
                              height(80.dp)
                      , placeholder = {
                      Text("Date")
                  })

            }
            Spacer(modifier = Modifier
                .weight(1f))

            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth(1f)){

                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                    ) {
                    Text(text = "Confirm")

                }
                Button(onClick = { /*TODO*/ }) {

                    Text(text = "Cancel")

                }

            }







        }







    }





}
