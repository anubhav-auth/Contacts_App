package com.example.contactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.contactsapp.data.ContactsDatabase
import com.example.contactsapp.presentation.ContactScreen
import com.example.contactsapp.presentation.ContactViewModel
import com.example.contactsapp.ui.theme.ContactsAppTheme

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactsDatabase::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactViewModel(database.dao) as T
                }
            }
        }
    )

//    private val viewModel by lazy {
//        ContactViewModel(database.dao)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsAppTheme {

                val state = viewModel.state.collectAsState()

                ContactScreen(state = state, event = viewModel::onEvent)
//                mypredialog()
//                mypre()
            }
        }
    }
}
