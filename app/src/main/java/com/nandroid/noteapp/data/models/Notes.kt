package com.nandroid.noteapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    var heading: String,
    var content: String,
    val dateOfCreation: String
) : Parcelable