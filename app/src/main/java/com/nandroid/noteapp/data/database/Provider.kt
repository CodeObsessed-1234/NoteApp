package com.nandroid.noteapp.data.database

import com.nandroid.noteapp.data.dao.NotesDao
import com.nandroid.noteapp.data.dao.UserDao

class Provider {
    companion object {
        fun provideNotesDao(appDatabase: AppDatabase): NotesDao {
            return appDatabase.notesDao()
        }

        fun provideUserDao(appDatabase: AppDatabase): UserDao {
            return appDatabase.userDao()
        }
    }
}