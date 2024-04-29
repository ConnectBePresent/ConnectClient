package com.example.connectcompose.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectcompose.Constants
import com.example.connectcompose.MainViewModel
import com.example.connectcompose.R
import com.example.connectcompose.StoreData
import com.example.connectcompose.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private lateinit var showAddStudentDialog: MutableState<Boolean>
private lateinit var showConfirmationDialog: MutableState<Boolean>

@Composable
fun IndividualStudentListScreen(navController: NavController, viewModel: MainViewModel) {

    showAddStudentDialog = remember { mutableStateOf(false) }
    showConfirmationDialog = remember { mutableStateOf(false) }

    val individualNavController = rememberNavController()

    MaterialTheme {
        Surface {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()

            Scaffold(floatingActionButton = {
                FloatingActionButton(onClick = {
                    showAddStudentDialog.value = true
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Add contact")
                }
            }) { padding ->
                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    ModalDrawerSheet(
                        modifier = if (drawerState.isOpen) Modifier
                            .fillMaxSize()
                            .padding(32.dp, 64.dp, 32.dp, 32.dp)
                        else if (drawerState.isAnimationRunning) Modifier.padding(16.dp)
                        else Modifier.padding(0.dp), drawerShape = RoundedCornerShape(16.dp)
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
                                        StoreData(context = navController.context).setIndividualUserName(
                                            ""
                                        )
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Logout"
                                )
                            }
                        }

                        NavigationDrawerItem(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                            label = { Text(text = "Student Report") },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                individualNavController.popBackStack(
                                    Constants.SCREEN_INDIVIDUAL_LIST, false
                                )
                                individualNavController.navigate(Constants.SCREEN_INDIVIDUAL_REPORT)
                            })

                        NavigationDrawerItem(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                            label = { Text(text = "Custom Message") },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                individualNavController.popBackStack(
                                    Constants.SCREEN_INDIVIDUAL_LIST, false
                                )
                                individualNavController.navigate(Constants.SCREEN_INDIVIDUAL_MESSAGE)
                            })
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
                                StudentList(viewModel)
                            }

                            composable(route = Constants.SCREEN_INDIVIDUAL_REPORT) {
                                StudentReport(individualNavController)
                            }
                            composable(route = Constants.SCREEN_INDIVIDUAL_MESSAGE) {
                                CustomMessage(individualNavController)
                            }
                        }

                        if (showAddStudentDialog.value) StudentAddDialog(viewModel)
                        if (showConfirmationDialog.value) ConfirmationDialog(viewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(viewModel: MainViewModel) {

    val absenteeList = remember { mutableStateListOf<Student>() }

    viewModel.getAbsenteeList()
        .observe(LocalViewModelStoreOwner.current as LifecycleOwner) { list ->
            absenteeList.clear()
            absenteeList.addAll(list.toMutableStateList())
        }

    Dialog(
        onDismissRequest = { showConfirmationDialog.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Start),
                text = "Confirm Absentees",
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(absenteeList) { student ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxSize(),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(all = 8.dp)
                        ) {
                            Text(
                                "${student.rollNumber} • ${student.name}",
                                modifier = Modifier.padding(all = 8.dp),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "Ph: ${student.phoneNumber}",
                                modifier = Modifier.padding(all = 8.dp),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }

            TextButton(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF292D32))
                    .padding(4.dp),
                onClick = {
                    viewModel.clearAbsenteeList()
                    showConfirmationDialog.value = false
                },
                content = {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("Confirm")
                            }
                        }, fontSize = 12.sp, fontWeight = FontWeight.Light
                    )
                },
                shape = RoundedCornerShape(32.dp)
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentList(viewModel: MainViewModel) {
    Column {

        val studentList = remember { mutableStateListOf<Student>() }

        val lifecycleOwner = LocalViewModelStoreOwner.current as LifecycleOwner

        LaunchedEffect(viewModel.getAbsenteeList()) {
            viewModel.getAllStudents()
                .observe(lifecycleOwner) { list ->
                    studentList.clear()
                    studentList.addAll(list.toMutableStateList())
                }
        }

        if (studentList.isEmpty() && viewModel.isAbsenteeListEmpty()) {
            Spacer(modifier = Modifier.weight(1.0f))

            Icon(
                painter = painterResource(id = R.drawable.no_data),
                "Submit",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(180.dp)
                    .padding(8.dp),
            )

            val pickPictureLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.GetContent()
            ) { fileUri ->
                if (fileUri == null) return@rememberLauncherForActivityResult

                viewModel.importData(fileUri) // FIXME: manual refresh
            }

            TextButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                border = BorderStroke(
                    1.dp, MaterialTheme.colorScheme.onSurface
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(
                        0xFF292D32
                    )
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(12.dp),
                onClick = {
                    pickPictureLauncher.launch("text/*")
                },
                content = {
                    Text(
                        text = "Import Class List from CSV File",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
            )

            Spacer(modifier = Modifier.weight(1.0f))

            return
        } else if (studentList.isEmpty()) {
            showConfirmationDialog.value = true
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(studentList) { student ->

                val state = rememberSwipeToDismissBoxState(
                    initialValue = SwipeToDismissBoxValue.Settled,
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.StartToEnd)
                            viewModel.addAbsentee(student)

                        studentList.remove(student)

                        true
                    }
                )

                SwipeToDismissBox(
                    state = state,
                    backgroundContent = @Composable {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 8.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    if (state.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
                                        Color.Red
                                    else
                                        Color.Green
                                ),
                        ) {
                            Icon(

                                imageVector = ImageVector.vectorResource(R.drawable.ic_person_off),
                                contentDescription = "Absent",
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(16.dp)
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_person_add),
                                contentDescription = "Present",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(16.dp)
                            )
                        }
                    },
                    enableDismissFromStartToEnd = true,
                    enableDismissFromEndToStart = true,
                    content = @Composable {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                            border = CardDefaults.outlinedCardBorder(),
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .fillMaxSize(),
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Column(
                                modifier = Modifier.padding(all = 8.dp)
                            ) {
                                Text(
                                    "${student.rollNumber} • ${student.name}",
                                    modifier = Modifier.padding(all = 8.dp),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Ph: ${student.phoneNumber}",
                                    modifier = Modifier.padding(all = 8.dp),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun StudentAddDialog(viewModel: MainViewModel) {

    Dialog(
        onDismissRequest = { showAddStudentDialog.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Start),
                text = "Add Student",
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
            )

            var rollNumber by remember { mutableIntStateOf(0) }
            var name by remember { mutableStateOf("") }
            var phoneNumber by remember { mutableStateOf("") }

            OutlinedTextField(modifier = Modifier.padding(8.dp),
                value = rollNumber.toString(),
                onValueChange = {
                    rollNumber = try {
                        it.trim().toInt()
                    } catch (e: NumberFormatException) {
                        0
                    }
                },
                label = {
                    Text(
                        "Roll Number"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = @Composable {
                    Icon(
                        painterResource(id = R.drawable.person), "person icon"
                    )
                })

            OutlinedTextField(modifier = Modifier.padding(8.dp),
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(
                        "Student Name"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = @Composable {
                    Icon(
                        painterResource(id = R.drawable.name), "name icon"
                    )
                })

            OutlinedTextField(modifier = Modifier.padding(8.dp),
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },
                label = {
                    Text(
                        "Parent Phone Number"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = @Composable {
                    Icon(
                        painterResource(id = R.drawable.phone), "phone icon"
                    )
                })

            var buttonText by remember { mutableStateOf("Add Student") }

            TextButton(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF292D32))
                    .padding(4.dp),
                onClick = {
                    GlobalScope.launch {
                        if (rollNumber == 0 || name.isEmpty() || phoneNumber.isEmpty()) {
                            buttonText = "Fields empty!!"
                            delay(1000)
                            buttonText = "Add Student"
                            return@launch
                        }

                        viewModel.insert(
                            Student(
                                rollNumber, name, phoneNumber
                            )
                        )

                        buttonText = "Success"
                        delay(500)

                        showAddStudentDialog.value = false
                    }
                },
                content = {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append(buttonText)
                            }
                        }, fontSize = 12.sp, fontWeight = FontWeight.Light
                    )
                },
                shape = RoundedCornerShape(32.dp)
            )

        }
    }
}