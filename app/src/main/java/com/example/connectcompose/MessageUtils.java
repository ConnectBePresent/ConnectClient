package com.example.connectcompose;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MessageUtils {

    public static void sendMessages(Context context, List<Student> absenteeList) {

        Toast.makeText(context, "Sending messages...", Toast.LENGTH_SHORT).show();

        for (Student student : absenteeList) {

            String text =
                    "NOTICE: Your ward " +
                            student.getName() +
                            " is absent from school for the day " +
                            new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                                    .format(Calendar.getInstance().getTime());

            SmsManager.getDefault()
                    .sendTextMessage(student.getPhoneNumber(),
                            null,
                            text,
                            null,
                            null
                    );
        }

        Toast.makeText(context, "Messages sent!", Toast.LENGTH_SHORT).show();
    }

}
