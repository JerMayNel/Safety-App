package com.example.safetyapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: Contacts)

    @Query("SELECT * FROM contacts ORDER BY id ASC")
    fun readAllContacts(): LiveData<List<Contacts>>

}