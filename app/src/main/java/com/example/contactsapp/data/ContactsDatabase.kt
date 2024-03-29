package com.example.contactsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Contacts::class],
    version = 1
)
abstract class ContactsDatabase : RoomDatabase() {
    abstract val dao : ContactsDAO
}