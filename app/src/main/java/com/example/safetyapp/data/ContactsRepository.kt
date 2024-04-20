package com.example.safetyapp.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepository(private val contactsDao: ContactsDao){

    val readAllData: LiveData<List<Contacts>> = contactsDao.readAllContacts()

    suspend fun addContact(contact: Contacts){
        contactsDao.insertContacts(contact)
    }
    suspend fun deleteContact(contact: Contacts) {
        withContext(Dispatchers.IO) {
            contactsDao.deleteContact(contact)
        }
    }
    suspend fun updateContactsIdsAfterDeletion(deletedId: Int) {
        val contactsAfterDeleted = contactsDao.getContactsAfter(deletedId)
        contactsAfterDeleted.forEachIndexed { index, contact ->
            val newId = deletedId + index
            contactsDao.updateId(contact.id, newId)
        }
    }

}