package com.example.contactsapp.presentation

data class ContactState(
    val contacts: List<com.example.contactsapp.data.Contacts> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)