package com.example.safetyapp.data

import androidx.lifecycle.LiveData

class ContactsRepository(private val contactsDao: ContactsDao){

    val readAllData: LiveData<List<Contacts>> = contactsDao.readAllContacts()

    suspend fun addContact(contact: Contacts){
        contactsDao.insertContacts(contact)
    }

}