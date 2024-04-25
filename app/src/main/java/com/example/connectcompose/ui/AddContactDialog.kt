package com.example.connectcompose.ui



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.connectcompose.ContactEvent
import com.example.connectcompose.ContactState

@Composable

fun DialogWithOutImage(
    onEvent: (ContactEvent) -> Unit,
    state: ContactState,
    modifier : Modifier
)
{
    Dialog(onDismissRequest = { onEvent(ContactEvent.HideDialog) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(Color.Black)
                .padding(10.dp),

            shape = RoundedCornerShape(16.dp)

        ){


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                TextField(
                    value = state.firstName,
                    onValueChange = {
                        onEvent(ContactEvent.SetFirstName(it))
                    },
                    placeholder = {
                        Text(text = "first name")
                    }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        onEvent(ContactEvent.SetPhoneNumber(it))
                    },
                    placeholder = {
                        Text(text = "Phone number")
                    }
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {
                        onEvent(ContactEvent.SaveContact)
                    }) {
                        Text(text = "Save")
                    }
                }
            }


        }
    }
}
