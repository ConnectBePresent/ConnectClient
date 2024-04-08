package com.example.connectcompose


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModel(
    private val dao : ContactDao
) : ViewModel() {


    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contact = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.FIRST_NAME -> dao.sortByFirstName()

                SortType.PHONE_NUMBER -> dao.sortByPhoneNumber()

            }

        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _contact, _sortType) { state, contact, sortType ->
        state.copy(
            contacts = contact,
            sortType = sortType

        )


    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }

            }

            ContactEvent.HideDialog -> {
                _state.update {
                    it.copy(isAddingContact = false)
                }
            }

            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val phoneNumber = state.value.phoneNumber

                if (firstName.isBlank() || phoneNumber.isBlank()) {
                    return
                }
                val contact = Contact(
                    firstName = firstName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch {
                    dao.insertContact(contact)
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        firstName = "",
                        phoneNumber = ""
                    )
                }


            }

            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(firstName = event.firstName)
                }
            }

            is ContactEvent.SetPhoneNumber -> {

                _state.update {
                    it.copy(phoneNumber = event.phoneNumber)
                }
            }

            ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(isAddingContact = true)
                }
            }

            is ContactEvent.SortContacts -> {

                _sortType.value = event.sortType
            }

            ContactEvent.HideMenu -> {

                _state.update {
                    it.copy(isMenuOpen = false)
                }


            }

            ContactEvent.ShowMenu -> {
                _state.update {
                    it.copy(isMenuOpen = true)
                }
            }

            ContactEvent.HideReport -> {
                _state.update {
                    it.copy(isReportOpen = false)
                }
            }

            ContactEvent.ShowReport -> {
                _state.update {
                    it.copy(isReportOpen = true)
                }
            }

            ContactEvent.HideMessage -> {
                _state.update {
                    it.copy(isMessageOpen = false)
                }
            }

            ContactEvent.ShowMessage -> {
                _state.update {
                    it.copy(isMessageOpen = true)
                }
            }

            is ContactEvent.SetMessage -> {

                _state.update {
                    it.copy(message = event.message)
                }


            }

        }

    }
}