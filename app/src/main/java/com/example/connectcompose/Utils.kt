package com.example.connectcompose

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Utils {

    companion object {
        fun getDate(): String {
            return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(Calendar.getInstance().time)
        }

        fun sendMessages(context: Context, absenteeList: List<Student>) {

//            if (BuildConfig.DEBUG) return

            Toast.makeText(context, "Sending messages...", Toast.LENGTH_SHORT).show()

            try {
                for ((_, name, phoneNumber) in absenteeList) {

                    val text = "NOTICE: Your ward " + name +
                            " is absent from school for the day " +
                            SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                                .format(Calendar.getInstance().time)

                    SmsManager.getDefault()
                        .sendTextMessage(
                            phoneNumber,
                            null,
                            text,
                            null,
                            null
                        )
                }

                Toast.makeText(context, "Messages sent!", Toast.LENGTH_SHORT).show()
            } catch (e: SecurityException) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        fun sendCustomMessage(context: Context, message: String, studentList: List<Student>) {

//            if (BuildConfig.DEBUG) return

            Toast.makeText(context, "Sending messages...", Toast.LENGTH_SHORT).show()

            try {
                for ((_, _, phoneNumber) in studentList) {
                    SmsManager.getDefault()
                        .sendTextMessage(
                            phoneNumber,
                            null,
                            message,
                            null,
                            null
                        )
                }

                Toast.makeText(context, "Messages sent!", Toast.LENGTH_SHORT).show()
            } catch (e: SecurityException) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

    }
}