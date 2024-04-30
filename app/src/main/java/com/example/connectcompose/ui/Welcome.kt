package com.example.connectcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connectcompose.Constants
import com.example.connectcompose.R

@Composable
fun Welcome(navController: NavController) {

    MaterialTheme {

        Surface(Modifier.fillMaxSize()) {
            Column {

                Text(
                    modifier = Modifier.padding(32.dp, 32.dp, 32.dp, 0.dp),
                    text = "Connect - Be Present",
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 32.sp,
                )

                Spacer(Modifier.weight(1f))

                Image(
                    modifier = Modifier
                        .padding(32.dp, 32.dp, 32.dp, 0.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(id = R.drawable.graduation),
                    contentDescription = "The image is of institute"
                )

                Spacer(Modifier.weight(1f))

                TextSection(navController = navController)
            }
        }
    }
}

@Composable
fun TextSection(navController: NavController) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onSurface),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            text = "User Mode",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface
        )

        Column(
            Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(Constants.SCREEN_INDIVIDUAL_LOGIN) {
                        popUpTo(Constants.SCREEN_WELCOME) { inclusive = true }
                    }
                }
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)) {
            Text(
                text = "Individual",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Here the platform is use individually and the user would have the full control of the system",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp
            )
        }

        Column(
            Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(Constants.SCREEN_INSTITUTE_LOGIN) {
                        popUpTo(Constants.SCREEN_WELCOME) { inclusive = true }
                    }
                }
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)) {
            Text(
                text = "Institute", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "The admin Panel is needed for the assigning the classroom and also provide the user with username and password",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp
            )
        }

    }
}