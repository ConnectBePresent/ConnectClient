package com.example.connectcompose

sealed interface ContactEvent {

    object SaveContact : ContactEvent
    data class SetFirstName(val firstName : String) : ContactEvent
    data class SetPhoneNumber(val phoneNumber: String) : ContactEvent
    data class SetMessage(val message: String): ContactEvent
    object ShowDialog : ContactEvent
    object HideDialog : ContactEvent
    data class SortContacts(val sortType: SortType) : ContactEvent
    data class DeleteContact(val contact : Contact) : ContactEvent
    data class AbsentContact(val contact: Contact) : ContactEvent
    data class DeleteFinalList(val contact: Contact): ContactEvent
    object ShowMenu : ContactEvent
    object HideMenu : ContactEvent
    object ShowReport : ContactEvent
    object HideReport : ContactEvent
    object ShowMessage : ContactEvent
    object HideMessage : ContactEvent
    object SendMessage : ContactEvent


}