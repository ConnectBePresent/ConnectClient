package com.example.connectcompose.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectcompose.AttendanceEntry
import com.example.connectcompose.Constants
import com.example.connectcompose.MainViewModel
import com.example.connectcompose.R
import com.example.connectcompose.SharedPreferenceHelper
import com.example.connectcompose.Student
import com.example.connectcompose.Utils
import com.example.connectcompose.application.Application
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private lateinit var showAddStudentDialog: MutableState<Boolean>
private lateinit var showConfirmationDialog: MutableState<Boolean>

private lateinit var attendanceStatus: MutableState<Int>

@Composable
fun StudentDetails(
    navController: NavController, viewModel: MainViewModel, firebaseDatabase: FirebaseDatabase
) {

    val mode = SharedPreferenceHelper.get(
        navController.context, Constants.USER_MODE
    )

    showAddStudentDialog = remember { mutableStateOf(false) }
    showConfirmationDialog = remember { mutableStateOf(false) }
    attendanceStatus = remember { mutableIntStateOf(Constants.ATTENDANCE_NOT_WAGED) }

    val individualNavController = rememberNavController()

    MaterialTheme {
        Surface {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()

            Scaffold(floatingActionButton = {
                if (mode == Constants.INDIVIDUAL_MODE) FloatingActionButton(onClick = {
                    showAddStudentDialog.value = true
                }) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add contact")
                }
            }) { padding ->
                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    ModalDrawerSheet(
                        modifier = if (drawerState.isOpen) Modifier
                            .fillMaxSize()
                            .padding(32.dp, 64.dp, 32.dp, 32.dp)
                        else if (drawerState.isAnimationRunning) Modifier.padding(16.dp)
                        else Modifier.padding(0.dp),
                        drawerShape = RoundedCornerShape(16.dp),
                        drawerContainerColor = MaterialTheme.colorScheme.background
                    ) {

                        var title by remember { mutableStateOf("Connect") }

                        LaunchedEffect(Unit) {
                            title =
                                if (mode == Constants.INSTITUTE_MODE) SharedPreferenceHelper.get(
                                    navController.context, Constants.INSTITUTE_EMAIL
                                ).replace(".com", "").uppercase()
                                else "Hi " + SharedPreferenceHelper.get(
                                    navController.context, Constants.INDIVIDUAL_USER_NAME
                                )
                        }

                        Row(Modifier.padding(16.dp)) {
                            Text(
                                text = title,
                                modifier = Modifier
                                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                                    .align(Alignment.CenterVertically),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                modifier = Modifier
                                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                                    .align(Alignment.CenterVertically),
                                onClick = {
                                    coroutineScope.launch {
                                        logout(navController, viewModel)
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Logout"
                                )
                            }
                        }

                        NavigationDrawerItem(
                            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                            label = { Text(text = "Student List") },
                            selected = individualNavController.currentDestination?.route == Constants.SCREEN_INDIVIDUAL_LIST,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.surface,
                                unselectedContainerColor = MaterialTheme.colorScheme.background,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                unselectedTextColor = MaterialTheme.colorScheme.onBackground
                            ),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                individualNavController.popBackStack(
                                    Constants.SCREEN_INDIVIDUAL_LIST, false
                                )
                            })

                        NavigationDrawerItem(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                            label = { Text(text = "Attendance History") },
                            selected = individualNavController.currentDestination?.route == Constants.SCREEN_INDIVIDUAL_HISTORY,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.surface,
                                unselectedContainerColor = MaterialTheme.colorScheme.background,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                unselectedTextColor = MaterialTheme.colorScheme.onBackground
                            ),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                individualNavController.popBackStack(
                                    Constants.SCREEN_INDIVIDUAL_LIST, false
                                )
                                individualNavController.navigate(Constants.SCREEN_INDIVIDUAL_HISTORY)
                            })

                        NavigationDrawerItem(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                            label = { Text(text = "Custom Message") },
                            selected = individualNavController.currentDestination?.route == Constants.SCREEN_INDIVIDUAL_MESSAGE,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.surface,
                                unselectedContainerColor = MaterialTheme.colorScheme.background,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                unselectedTextColor = MaterialTheme.colorScheme.onBackground
                            ),
                            shape = RoundedCornerShape(8.dp),
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

                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        logout(navController, viewModel)
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Logout"
                                )
                            }
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

                            composable(route = Constants.SCREEN_INDIVIDUAL_HISTORY) {
                                AttendanceHistory(
                                    individualNavController, viewModel, firebaseDatabase
                                )
                            }
                            composable(route = Constants.SCREEN_INDIVIDUAL_MESSAGE) {
                                CustomMessage(individualNavController, viewModel)
                            }
                        }

                        if (showAddStudentDialog.value) StudentAddDialog(viewModel)
                        if (showConfirmationDialog.value) ConfirmationDialog(
                            navController, viewModel, firebaseDatabase
                        )
                    }
                }
            }
        }
    }
}

fun logout(navController: NavController, viewModel: MainViewModel) {
    SharedPreferenceHelper.clearAll(navController.context)
    viewModel.clearAll()

    navController.navigate(Constants.SCREEN_WELCOME) {
        popUpTo(Constants.SCREEN_STUDENT_DETAILS) { inclusive = true }
    }
}

@Composable
fun ConfirmationDialog(
    navController: NavController, viewModel: MainViewModel, firebaseDatabase: FirebaseDatabase
) {

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
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {

            Column(Modifier.padding(16.dp)) {

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Start),
                    text = "Confirm Absentees",
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(absenteeList.sortedBy { it.rollNumber },
                        key = { it.rollNumber }) { StudentCard(it) }
                }

                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean -> // FIXME: doesn't get called for some reason
                    if (isGranted) {
                        Utils.sendMessages(
                            viewModel.getApplication<Application>().applicationContext, absenteeList
                        )
                    } else {
                        Toast.makeText(
                            navController.context,
                            "App requires SMS permission to work!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                TextButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(12.dp),
                    onClick = {

                        if (ContextCompat.checkSelfPermission(
                                navController.context, Manifest.permission.SEND_SMS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            Utils.sendMessages(
                                viewModel.getApplication<Application>().applicationContext,
                                absenteeList
                            )
                        } else {
                            launcher.launch(Manifest.permission.SEND_SMS)
                        }

                        val attendanceEntry = AttendanceEntry(Utils.getDate(), absenteeList)

                        viewModel.insert(attendanceEntry) // FIXME

                        val mode = SharedPreferenceHelper.get(
                            navController.context, Constants.USER_MODE
                        )

                        if (mode == Constants.INSTITUTE_MODE) pushAttendanceDetails(
                            navController, attendanceEntry, firebaseDatabase
                        )

                        viewModel.clearAbsenteeList()
                        attendanceStatus.value = Constants.ATTENDANCE_WAGED

                        showConfirmationDialog.value = false
                    },
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                    append("Confirm")
                                }
                            }, fontSize = 16.sp, fontWeight = FontWeight.Light
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun StudentCard(student: Student) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
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
                text = "Ph: ${student.parentPhoneNumber}",
                modifier = Modifier.padding(all = 8.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }

}

fun pushAttendanceDetails(
    navController: NavController,
    attendanceEntry: AttendanceEntry,
    firebaseDatabase: FirebaseDatabase
) {
    Toast.makeText(
        navController.context, "Pushing Attendance Data...", Toast.LENGTH_LONG,
    ).show()

    val email = SharedPreferenceHelper.get(navController.context, Constants.INSTITUTE_EMAIL)
        .replace(".com", "") // cuz firebase doesn't support "." in it's paths

    val reference = firebaseDatabase.getReference("attendance")

    reference.keepSynced(true)

    reference.child(email).child(Utils.getDate()).setValue(attendanceEntry.absenteeList)
        .addOnSuccessListener {
            Toast.makeText(
                navController.context,
                "Push successful!",
                Toast.LENGTH_SHORT,
            ).show()
        }.addOnFailureListener {
            Log.e("vishnu", "pushAttendanceDetails: ", it)
            Toast.makeText(
                navController.context, "Something went wrong!", Toast.LENGTH_SHORT,
            ).show()
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentList(viewModel: MainViewModel) {
    Column()
//    (Modifier.verticalScroll(rememberScrollState()))
    {

        val today = Utils.getDate()

        val absenteeList = remember { mutableStateListOf<Student>() }

        LaunchedEffect(attendanceStatus.value) {
            viewModel.getAttendanceEntry(today).observeForever {
                absenteeList.clear()

                if (it?.absenteeList != null) absenteeList.addAll(it.absenteeList.toMutableList())
            }
        }

        if (absenteeList.isNotEmpty()) {

            attendanceStatus.value = Constants.ATTENDANCE_WAGED

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Start),
                text = "Today's Absentees",
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(absenteeList.sortedBy { it.rollNumber },
                    key = { it.rollNumber }) { StudentCard(it) }
            }

        }

        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),
            text = "Student List",
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 24.sp,
        )

        val studentList = remember { mutableStateListOf<Student>() }

        LaunchedEffect(attendanceStatus.value) {
            viewModel.getAllStudents().observeForever { list ->

                if (list.isEmpty()) return@observeForever

                studentList.clear()
                studentList.addAll(list.toMutableStateList())

                attendanceStatus.value = Constants.ATTENDANCE_WAGING
            }
        }

        if (studentList.isEmpty() && attendanceStatus.value == Constants.ATTENDANCE_NOT_WAGED) {
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
        } else if (studentList.isEmpty() && attendanceStatus.value == Constants.ATTENDANCE_WAGING) {
            showConfirmationDialog.value = true
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(studentList.sortedBy { it.rollNumber }, key = { it.rollNumber }) { student ->

                val state =
                    rememberSwipeToDismissBoxState(initialValue = SwipeToDismissBoxValue.Settled,
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.StartToEnd) {
                                viewModel.addAbsentee(student)
                                studentList.remove(student)
                            } else if (it == SwipeToDismissBoxValue.EndToStart) studentList.remove(
                                student
                            )
                            true
                        })

                SwipeToDismissBox(state = state,
                    backgroundContent = @Composable {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 8.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    if (state.dismissDirection == SwipeToDismissBoxValue.StartToEnd) Color.Red
                                    else Color.Green
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
                    enableDismissFromStartToEnd = attendanceStatus.value != Constants.ATTENDANCE_WAGED,
                    enableDismissFromEndToStart = attendanceStatus.value != Constants.ATTENDANCE_WAGED,
                    content = { StudentCard(student) })
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
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {

            Column(Modifier.padding(16.dp)) {

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Start),
                    text = "Add Student",
                    fontWeight = FontWeight.Medium,
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
                            painterResource(id = R.drawable.ic_person), "person icon"
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
                            painterResource(id = R.drawable.ic_badge), "name icon"
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
                            painterResource(id = R.drawable.ic_phone), "phone icon"
                        )
                    })

                var buttonText by remember { mutableStateOf("Add Student") }

                TextButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(12.dp),
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
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                    append(buttonText)
                                }
                            }, fontSize = 16.sp, fontWeight = FontWeight.Light
                        )
                    },
                )

            }
        }
    }
}