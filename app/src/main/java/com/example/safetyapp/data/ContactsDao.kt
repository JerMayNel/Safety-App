package com.example.safetyapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: Contacts)

    @Query("SELECT * FROM contacts ORDER BY id ASC")
    fun readAllContacts(): LiveData<List<Contacts>>

    @Delete
    suspend fun deleteContact(contacts: Contacts)

    @Query("UPDATE contacts SET id = :newId WHERE id = :oldId")
    suspend fun updateId(oldId: Int, newId: Int)

    @Query("SELECT * FROM contacts WHERE id > :deletedId")
    suspend fun getContactsAfter(deletedId: Int): List<Contacts>

    @Query("SELECT * FROM contacts WHERE id = 1")
    suspend fun getEmergencyContacts(): List<Contacts>

    @Query("SELECT * FROM contacts WHERE number = :number")
    fun getContactsByNumber(number: String): Contacts
}