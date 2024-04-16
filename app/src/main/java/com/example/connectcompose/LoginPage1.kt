package com.example.connectcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Loginpage1(
    modifier : Modifier,
    navController : NavController
){
    Column (modifier = modifier){
        Row(modifier = modifier
            .fillMaxWidth(1f)
            .padding(10.dp)
            ) {
            Image(modifier  = modifier
                ,
                  painter = painterResource(id = R.drawable.educator),
                contentDescription = "The image is of institute"
            )



        }
        Row(modifier = modifier
            .fillMaxWidth(1f)
        ){

           TextSection(modifier = modifier,
               navController = navController)

        }
    }
}

@Composable
fun TextSection(modifier : Modifier,
                navController : NavController){
    
    Column(modifier = modifier
        .fillMaxHeight(1f)
        .padding(30.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(colorResource(id = R.color.purple_200))
        ,
        verticalArrangement = Arrangement.SpaceBetween){
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = "Choose from individual and Institute level",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.Black)
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column {
                Text(text = "Individual"
                    ,color = Color.Black,
                    fontSize = 20.sp)
                Text("Here the platform is use individually and the user would have the full control of the system"
                    ,color = Color.Black,
                    fontSize = 15.sp)
            }

        }
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column {
                Text(text = "Institute"
                ,color = Color.Black,
                    fontSize = 20.sp
                )
                Text("The admin Panel is needed for the assigning the classroom " +
                        "and also provide the user with username and password",
                    color = Color.Black,
                    fontSize = 15.sp)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp)
        ){
            Button(onClick = {

                navController.navigate(Screen.Welcome.route)


            }) {
                Text(text = "Individual"
                    ,color = Color.Black,
                    fontSize = 20.sp)
                
            }
            Button(onClick = {

                navController.navigate(Screen.LoginPage2.route)

            }) {
                Text(text = "Institute"
                    ,color = Color.Black,
                    fontSize = 20.sp)
            }
        }



    }

}