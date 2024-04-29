package com.example.connectcompose


import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.connectcompose.application.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainViewModel(
    private val application: Application,
    private val repository: StudentRepository
) :
    AndroidViewModel(application) {

    private val absenteeList: ArrayList<Student> = ArrayList()
    private var absenteeListLiveData: LiveData<List<Student>> = MutableLiveData(absenteeList)

    private val studentsListLiveData: LiveData<List<Student>> = repository.getAllStudents()

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(student)
    }

    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(student)
    }

    fun update(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(student)
    }

    fun getAllStudents(): LiveData<List<Student>> {
        return studentsListLiveData
    }

    fun addAbsentee(student: Student) {
        absenteeList.add(student)
    }

    fun isAbsenteeListEmpty(): Boolean {
        return absenteeList.isEmpty()
    }

    fun clearAbsenteeList() {
        absenteeList.clear()
        absenteeListLiveData = MutableLiveData(absenteeList)
    }

    fun getAbsenteeList(): LiveData<List<Student>> {
        return absenteeListLiveData
    }

    private val attendanceListLiveData: LiveData<List<AttendanceEntry>> =
        repository.getAllAttendanceEntries()

    fun insert(attendanceEntry: AttendanceEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(attendanceEntry)
    }

    fun delete(attendanceEntry: AttendanceEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(attendanceEntry)
    }

    fun update(attendanceEntry: AttendanceEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(attendanceEntry)
    }

    fun getAbsentees(date: String): AttendanceEntry? {
        var attendanceEntry: AttendanceEntry? = null

        viewModelScope.launch {
            attendanceEntry = repository.getAbsentees(date)
        }

        return attendanceEntry
    }

    fun getAllAttendanceEntries(): LiveData<List<AttendanceEntry>> {
        return attendanceListLiveData
    }


    fun importData(data: Uri) {
        try {
            val bufferedReader = BufferedReader(
                InputStreamReader(
                    application.contentResolver
                        .openInputStream(data)
                )
            )

            bufferedReader.forEachLine {
                val tokens = it.split(",")

                insert(
                    Student(
                        tokens[0].toInt(), tokens[1], tokens[2]
                    )
                )
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application, application.repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}