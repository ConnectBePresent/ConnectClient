package com.example.connectcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Welcome(modifier : Modifier,
            navController: NavController){


    val name = remember {

        mutableStateOf("Guest")
    }


    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ){

        Row {
             Text("Hi,\n\n\nWELCOME TO\n\n\nCONNECT",
                 fontSize = 50.sp,
                 color = Color.White,
                 modifier = Modifier
                     )

        }
        Row {
            Text(text = "What should we call you",
                fontSize = 20.sp,
                color = Color.White)

        }
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)

        ) {

            TextField(value = name.value, onValueChange = {
                 name.value = it
            },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    ,
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                )



        }
        Spacer(modifier = Modifier
            .weight(1f))
        Row (
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.End


        ){
              Button(onClick = {
                  navController.navigate(route = Screen.StudentEntry.route
                  )
              }) {
                  Text("Next")

              }
        }




    }



}