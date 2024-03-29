package com.example.contactsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contacts(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String
)
