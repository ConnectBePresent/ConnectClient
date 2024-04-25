package com.example.connectcompose.ui


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectcompose.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private fun signIn(email: String, password: String,auth: FirebaseAuth) {
    // [START sign_in_with_email]

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("working", "signInWithEmail:success")
                val user = auth.currentUser



            } else {
                // If sign in fails, display a message to the user.
                Log.w("working", "signInWithEmail:failure", task.exception)



            }


        }

}



@Composable
fun Loginpage2(
    modifier : Modifier,
    auth : FirebaseAuth
){
    var instituteId by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginStatus by remember { mutableStateOf("") }


    /// older code used for login in remove if not needed

    fun checkLoginCredentials(
        instituteId: String,
        teacherEmail: String,
        teacherPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val dbReference = Firebase.database.reference.child("institutes").child(instituteId)

        dbReference.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val classes = dataSnapshot.child("classList").children
                val validLogin = classes.any { classData ->
                    classData.child("teacherEmail").value == teacherEmail &&
                            classData.child("teacherPassword").value == teacherPassword
                }
                if (validLogin) onSuccess() else onFailure("Invalid credentials.")
            } else {
                onFailure("Institute not found.")
            }
        }.addOnFailureListener {
            onFailure("Failed to access Firebase Database.")
        }
    }

 ///// Older code used for login



    Column (modifier = modifier){
        Row(modifier = modifier
            .fillMaxWidth(1f)
            .padding(10.dp)
        ) {
            Image(modifier  = modifier
                ,
                painter = painterResource(id = R.drawable.loginimages),
                contentDescription = "The image is of institute"
            )



        }
        Row(modifier = modifier
            .fillMaxWidth(1f)

            .fillMaxHeight(1f)
            .padding(20.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
        ){

            Column(modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {


                Row(modifier = Modifier
                    .padding(15.dp)) {

                    Column(modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Login",fontSize= 30.sp, color = Color.Black)

                        TextField(value = instituteId, onValueChange = { instituteId = it }, placeholder = { Text("Institute ID")})
                        TextField(value = email, onValueChange = { email = it }, label = { Text("Username")})
                        TextField(value = password, onValueChange = { password = it },visualTransformation = PasswordVisualTransformation(), placeholder = { Text("Password")})

                    }

                }



                Row (
                    modifier = Modifier

                        .fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(onClick = {

                    signIn(email,password,auth)

                    }) {
                        Text(text = "Login",
                            color = Color.Black,
                            modifier = Modifier
                                .padding(10.dp))
                    }
                    Text(loginStatus)
                }
                }

            }





        }
    }

