package com.example.contactsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.data.Contacts
import com.example.contactsapp.data.ContactsDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val dao: ContactsDAO
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _state = MutableStateFlow(ContactState())
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _contacts = _sortType.flatMapLatest {
        sortType ->
        when(sortType){
            SortType.FIRST_NAME -> dao.orderByFirstName()
            SortType.LAST_NAME -> dao.orderByLastName()
            SortType.PHONE_NUMBER -> dao.orderByPhoneNumber()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _contacts, _sortType){
        state, contacts, sortType ->

        state.copy(
            contacts = contacts,
            sortType = sortType
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactsEvents){
        when(event){
            is ContactsEvents.deleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contacts)
                }
            }
            ContactsEvents.hideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }
            }
            ContactsEvents.saveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber
                val email = state.value.email

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || email.isBlank()){
                    return
                }

                val contacts = Contacts(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    email = email
                )

                viewModelScope.launch {
                    dao.saveContact(contacts)
                }

                _state.update {
                    it.copy(
                        firstName = "",
                        lastName = "",
                        phoneNumber = "",
                        email = "",
                        isAddingContact = false
                    )
                }


            }
            is ContactsEvents.setFirstName -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }
            }
            is ContactsEvents.setLastName -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }
            is ContactsEvents.setPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            ContactsEvents.showDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }
            is ContactsEvents.sortContacts -> {
                _sortType.value = event.sortType
            }

            is ContactsEvents.setEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
        }
    }
}