package com.example.contactsapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDAO{

    @Upsert
    suspend fun saveContact(contacts: Contacts)

    @Delete
    suspend fun deleteContact(contacts: Contacts)

    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun orderByFirstName() : Flow<List<Contacts>>

    @Query("SELECT * FROM contacts ORDER BY lastName ASC")
    fun orderByLastName() : Flow<List<Contacts>>

    @Query("SELECT * FROM contacts ORDER BY phoneNumber ASC")
    fun orderByPhoneNumber() : Flow<List<Contacts>>

}