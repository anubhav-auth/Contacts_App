package com.example.contactsapp.presentation

import com.example.contactsapp.data.Contacts


sealed interface ContactsEvents{
    object saveContact : ContactsEvents
    data class setFirstName(val firstName: String) : ContactsEvents
    data class setLastName(val lastName: String) : ContactsEvents
    data class setPhoneNumber(val phoneNumber: String) : ContactsEvents
    data class setEmail(val email: String) : ContactsEvents
    object showDialog : ContactsEvents
    object hideDialog : ContactsEvents
    data class sortContacts(val sortType: SortType) : ContactsEvents
    data class deleteContact(val contacts: Contacts) : ContactsEvents
}