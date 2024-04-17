package com.example.connectcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun LoginPage(
    modifier: Modifier, navController: NavController
) {

    Surface(Modifier.fillMaxSize()) {
        Column {

            Text(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                text = "Connect - Be Present",
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 32.sp,
            )

            Image(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                painter = painterResource(id = R.drawable.educator),
                contentDescription = "The image is of institute"
            )

            TextSection(navController = navController)
        }
    }
}

@Composable
fun TextSection(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceBright),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "User Mode",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(Modifier
            .padding(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { navController.navigate(Screen.Welcome.route) }
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
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp
            )
        }

        Column(
            Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(Screen.LoginPage2.route)
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