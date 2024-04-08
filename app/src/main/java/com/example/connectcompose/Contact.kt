package com.example.connectcompose

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Contact(
    val firstName : String,
    val phoneNumber : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)
