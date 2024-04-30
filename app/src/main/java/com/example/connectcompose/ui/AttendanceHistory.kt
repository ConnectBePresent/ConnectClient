package com.example.connectcompose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.connectcompose.MainViewModel
import com.example.connectcompose.Student
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AttendanceHistory(individualNavController: NavHostController, viewModel: MainViewModel) {

    Column {

        val calendarState = rememberSelectableCalendarState(
            maxMonth = YearMonth.now()
        )

        SelectableCalendar(
            firstDayOfWeek = DayOfWeek.MONDAY,
            calendarState = calendarState,
            monthHeader = { MonthHeader(it) },
            dayContent = { Day(it) },
            horizontalSwipeEnabled = false
        )

        val date = remember { mutableStateOf("") }
        val absenteeList = remember { mutableStateListOf<Student>() }

        LaunchedEffect(calendarState.selectionState.selection) {

            if (calendarState.selectionState.selection.isEmpty()) {
                absenteeList.clear()
                return@LaunchedEffect
            }

            date.value = calendarState.selectionState.selection[0].format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
            )

            viewModel.getAttendanceEntry(date.value).observeForever {
                absenteeList.clear()

                if (it?.absenteeList != null) absenteeList.addAll(it.absenteeList)
            }
        }

        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),
            text = "Absentees on ${date.value}",
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 24.sp,
        )

        LazyColumn {
            items(absenteeList.sortedBy { it.rollNumber }, key = { it.rollNumber }) { student ->
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
                            text = "Ph: ${student.parentPhoneNumber}",
                            modifier = Modifier.padding(all = 8.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun <T : SelectionState> Day(
    state: DayState<T>,
    onClick: (LocalDate) -> Unit = {},
) {

    val isSelected = state.selectionState.isDateSelected(state.date)

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )
            .aspectRatio(1f)
            .border(
                if (state.isCurrentDay) BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
                else BorderStroke(0.dp, MaterialTheme.colorScheme.surface)
            )
            .clickable {
                onClick(state.date)
                state.selectionState.onDateSelected(state.date)
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = state.date.dayOfMonth.toString(),
            color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}

@Composable
fun MonthHeader(monthState: MonthState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DecrementButton(monthState = monthState)

        Text(modifier = Modifier
            .padding(8.dp)
            .testTag("MonthLabel"),
            text = monthState.currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                .lowercase()
                .replaceFirstChar { it.titlecase() } + " " + monthState.currentMonth.year.toString(),
            fontSize = 24.sp)

        IncrementButton(monthState = monthState)
    }
}

@Composable
private fun DecrementButton(
    monthState: MonthState,
) {
    IconButton(modifier = Modifier.testTag("Decrement"),
        enabled = monthState.currentMonth > monthState.minMonth,
        onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = "Previous",
        )
    }
}

@Composable
private fun IncrementButton(
    monthState: MonthState,
) {
    IconButton(modifier = Modifier.testTag("Increment"),
        enabled = monthState.currentMonth < monthState.maxMonth,
        onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = "Next",
        )
    }
}