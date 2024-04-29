package com.example.connectcompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectcompose.Constants
import com.example.connectcompose.StoreData
import kotlinx.coroutines.launch

@Composable
fun IndividualStudentListScreen(navController: NavController) {

    val individualNavController = rememberNavController()

    MaterialTheme {
        Surface {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()

            Scaffold(floatingActionButton = {
                FloatingActionButton(onClick = { /*onEvent(ContactEvent.ShowDialog)*/ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Add contact")
                }
            }) { padding ->
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = if (drawerState.isOpen) Modifier
                                .fillMaxSize()
                                .padding(32.dp, 64.dp, 32.dp, 32.dp)
                            else if (drawerState.isAnimationRunning)
                                Modifier.padding(16.dp)
                            else
                                Modifier.padding(0.dp),
                            drawerShape = RoundedCornerShape(16.dp)
                        ) {

                            Row(Modifier.padding(16.dp)) {
                                Text(
                                    text = "Connect",
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    fontSize = 18.sp
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            StoreData(context = navController.context)
                                                .setIndividualUserName(
                                                    ""
                                                )
                                        }
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "Logout"
                                    )
                                }
                            }

                            NavigationDrawerItem(
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                label = { Text(text = "Student Report") },
                                selected = false,
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    individualNavController.popBackStack(
                                        Constants.SCREEN_INDIVIDUAL_LIST,
                                        false
                                    )
                                    individualNavController.navigate(Constants.SCREEN_INDIVIDUAL_REPORT)
                                }
                            )

                            NavigationDrawerItem(
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                label = { Text(text = "Custom Message") },
                                selected = false,
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    individualNavController.popBackStack(
                                        Constants.SCREEN_INDIVIDUAL_LIST,
                                        false
                                    )
                                    individualNavController.navigate(Constants.SCREEN_INDIVIDUAL_MESSAGE)
                                }
                            )
                        }
                    }) {

                    Column {
                        Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu icon to access other screens"
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Connect",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 24.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))
                        }

                        NavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            navController = individualNavController,
                            startDestination = Constants.SCREEN_INDIVIDUAL_LIST
                        ) {
                            composable(route = Constants.SCREEN_INDIVIDUAL_LIST) {
                                StudentList(individualNavController)
                            }

                            composable(route = Constants.SCREEN_INDIVIDUAL_REPORT) {
                                StudentReport(individualNavController)
                            }
                            composable(route = Constants.SCREEN_INDIVIDUAL_MESSAGE) {
                                CustomMessage(individualNavController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomMessage(individualNavController: NavHostController) {
    Text("Custom Message")
}

@Composable
fun StudentReport(individualNavController: NavHostController) {
    Text("Student Report")
}

@Composable
fun StudentList(individualNavController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            Text(it.toString())
        }
    }
}
