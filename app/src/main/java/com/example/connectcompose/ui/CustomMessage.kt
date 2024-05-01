package com.example.connectcompose.ui

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.connectcompose.Constants
import com.example.connectcompose.MainViewModel
import com.example.connectcompose.Utils

@Composable
fun CustomMessage(individualNavController: NavHostController, viewModel: MainViewModel) {

    val suffix = "\n- by Class Teacher (automated)"

    var message by remember { mutableStateOf(suffix) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        Text(
            text = "Custom Message",
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            text = "Warning: you are about to notify all your students!",
            Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            label = { Text("Custom Message") },
            value = message,
            onValueChange = {
                message = it.replace(suffix, "") + suffix
            },
            singleLine = false,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean -> // FIXME: doesn't get called for some reason
            if (isGranted) {
                viewModel.getAllStudents().observeForever {
                    if (it.isEmpty())
                        return@observeForever

                    Utils.sendCustomMessage(
                        individualNavController.context,
                        message,
                        it,
                    )

                    individualNavController.popBackStack(
                        Constants.SCREEN_INDIVIDUAL_LIST, false
                    )
                }
            } else {
                Toast.makeText(
                    individualNavController.context,
                    "App requires SMS permission to work!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        Button(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .align(Alignment.End),
            onClick = {
                if (message.isEmpty())
                    return@Button

                if (ContextCompat.checkSelfPermission(
                        individualNavController.context, Manifest.permission.SEND_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel.getAllStudents().observeForever {
                        if (it.isEmpty())
                            return@observeForever

                        Utils.sendCustomMessage(
                            individualNavController.context,
                            message,
                            it,
                        )

                        individualNavController.popBackStack(
                            Constants.SCREEN_INDIVIDUAL_LIST, false
                        )
                    }
                } else {
                    launcher.launch(Manifest.permission.SEND_SMS)
                }
            }) { Text("Send") }
    }

}