package com.example.connectcompose

import androidx.compose.material3.ExperimentalMaterial3Api

data class ContactState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val phoneNumber: String = "",
    val message: String = "",
    val isAddingContact: Boolean = false,
    val isMenuOpen: Boolean = false,
    val isReportOpen: Boolean = false,
    val isMessageOpen: Boolean = false,
    val absent: List<Contact> = mutableListOf(),
    val sortType: SortType = SortType.FIRST_NAME,


    )
