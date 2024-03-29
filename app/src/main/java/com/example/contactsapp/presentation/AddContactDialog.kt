package com.example.contactsapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddContactDialog(state: ContactState, onEvent: (ContactsEvents) -> Unit) {
    Scaffold {
        AlertDialog(
            modifier = Modifier.padding(it),
            onDismissRequest = { onEvent(ContactsEvents.hideDialog) },
            title = { Text(text = "Add contact") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(value = state.firstName,
                        onValueChange = { onEvent(ContactsEvents.setFirstName(it)) },
                        label = { "First name" },
                        placeholder = { "First name" })
                    TextField(value = state.lastName,
                        onValueChange = { onEvent(ContactsEvents.setLastName(it)) },
                        placeholder = { "lastName" })
                    TextField(value = state.email,
                        onValueChange = { onEvent(ContactsEvents.setEmail(it)) },
                        placeholder = { "email" })
                    TextField(value = state.phoneNumber,
                        onValueChange = { onEvent(ContactsEvents.setPhoneNumber(it)) },
                        placeholder = { "phoneNumber" })
                }
            },

            confirmButton = {
                Button(
                    onClick = { onEvent(ContactsEvents.saveContact) }
                ) {
                    Text(text = "Save Contact")
                }
            }
        )

    }
}