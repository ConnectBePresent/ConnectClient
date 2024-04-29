package com.example.connectcompose


import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    private val studentsList: LiveData<List<Student>> = repository.getAll()

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
        return studentsList
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