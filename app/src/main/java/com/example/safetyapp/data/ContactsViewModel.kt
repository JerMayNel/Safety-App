package com.example.safetyapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Contacts>>
    private val repository: ContactsRepository
    init {
        val contactsDao = ContactsDatabase.getDatabase(application).contactsDao()
        repository = ContactsRepository(contactsDao)
        readAllData = repository.readAllData
    }

    fun addContact(contact: Contacts) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addContact(contact) }
    }
    fun deleteContact(contact: Contacts) {
        viewModelScope.launch {
            repository.deleteContact(contact)
            // After deleting, update the IDs of remaining contacts
            repository.updateContactsIdsAfterDeletion(contact.id)
        }
    }

}