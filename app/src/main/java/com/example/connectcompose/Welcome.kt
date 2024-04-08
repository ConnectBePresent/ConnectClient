package com.example.connectcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Welcome(modifier : Modifier,
            navController: NavController){


    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Row {
             Text("Hi,\n\nWELCOME TO CONNECT",
                 fontSize = 30.sp,
                 color = Color.Black)

        }
        Row {
            Text(text = "What should we call you",
                fontSize = 20.sp,
                color = Color.Black)

        }
        Row(modifier = Modifier
            .background(color = colorResource(id = R.color.purple_200))
            .border(1.dp, color = Color.White, shape = RoundedCornerShape(30.dp))
            .fillMaxWidth(1f)
            .padding(10.dp)
            .requiredHeight(100.dp)
            
            ) {
           Box(){
               Text(text = "Your name here")
           }
        }
        Row {
              Button(onClick = {
                  navController.navigate(route = Screen.StudentEntry.route
                  )
              }) {
                  Text("Next")

              }
        }




    }



}