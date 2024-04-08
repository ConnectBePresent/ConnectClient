package com.example.connectcompose

data class ContactState(
    val contacts : List<Contact> = emptyList(),
    val firstName : String = "",
    val phoneNumber : String = "",
    val message: String = "",
    val isAddingContact : Boolean = false,
    val isMenuOpen: Boolean = false,
    val isReportOpen: Boolean = false,
    val isMessageOpen: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME



)
