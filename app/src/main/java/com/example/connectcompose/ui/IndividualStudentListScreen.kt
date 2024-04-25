package com.example.connectcompose.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connectcompose.ContactEvent
import com.example.connectcompose.ContactState
import com.example.connectcompose.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualStudentListScreen(
    state: ContactState, onEvent: (ContactEvent) -> Unit, navController: NavController
) {
    MaterialTheme {
        Surface {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()

            Scaffold(floatingActionButton = {
                FloatingActionButton(onClick = { onEvent(ContactEvent.ShowDialog) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Add contact")
                }
            }) { padding ->
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier =
                            if (drawerState.isOpen)
                                Modifier
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
                                        navController.navigate(Screen.FinalList.route)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Move to final list of absent students"
                                    )
                                }
                            }

                            NavigationDrawerItem(
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                label = { Text(text = "Student List") },
                                selected = false,
                                onClick = { coroutineScope.launch { drawerState.close() } }
                            )

                            NavigationDrawerItem(
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                label = { Text(text = "Import CSV") },
                                selected = false,
                                onClick = { /*TODO*/ }
                            )

                            NavigationDrawerItem(
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                label = { Text(text = "Student Report") },
                                selected = false,
                                onClick = { navController.navigate(Screen.ReportFrag.route) }
                            )

                            NavigationDrawerItem(
                                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                label = { Text(text = "Custom Message") },
                                selected = false,
                                onClick = { navController.navigate(Screen.MessageFrag.route) }
                            )
                        }
                    }) {

                    if (state.isAddingContact) {
                        DialogWithOutImage(state = state, onEvent = onEvent, modifier = Modifier)
                    }

                    val dateState = rememberDatePickerState()

                    if (state.isReportOpen) {
                        DatePickerDialog(onDismissRequest = { }, confirmButton = {
                            TextButton(onClick = { onEvent(ContactEvent.HideReport) }) {
                                Text(text = "Save")
                            }
                        }) {
                            DatePicker(state = dateState)
                        }
                    }

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

                        IconButton(
                            onClick = {
                                navController.navigate(Screen.FinalList.route)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Move to final list of absent students"
                            )
                        }
                    }

                    LazyColumn(
                        contentPadding = padding,
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.contacts) { contact ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(contact.firstName, fontSize = 20.sp)
                                    Text(contact.phoneNumber, fontSize = 12.sp)
                                }
                                IconButton(onClick = {
                                    onEvent(ContactEvent.AbsentContact(contact))
                                    for (i in state.absent) {
                                        Log.d("Absent", i.firstName)
                                    }
                                    onEvent(ContactEvent.DeleteContact(contact))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Contact"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}