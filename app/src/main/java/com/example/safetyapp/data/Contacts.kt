package com.example.safetyapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val number : String,
    val relation : String
): Parcelable
