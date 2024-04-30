package com.example.connectcompose.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.connectcompose.AttendanceEntry
import com.example.connectcompose.Converter
import com.example.connectcompose.Student

@Database(
    entities = [Student::class, AttendanceEntry::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context, name: String): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}