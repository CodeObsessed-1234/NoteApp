package com.nandroid.noteapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nandroid.noteapp.data.models.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Notes)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): List<Notes>

    @Query("DELETE FROM notes_table WHERE noteId = :id")
    suspend fun deleteNote(id: Int)

    @Update
    suspend fun updateNote(note: Notes)

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    fun getNote(id: Int): Notes

}