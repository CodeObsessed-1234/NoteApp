package com.nandroid.noteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nandroid.noteapp.data.dao.NotesDao
import com.nandroid.noteapp.data.dao.UserDao
import com.nandroid.noteapp.data.models.Notes
import com.nandroid.noteapp.data.models.User

@Database(entities = [Notes::class, User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun userDao(): UserDao
}