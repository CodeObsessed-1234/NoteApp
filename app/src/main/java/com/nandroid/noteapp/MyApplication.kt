package com.nandroid.noteapp

import android.app.Application
import androidx.room.Room
import com.nandroid.noteapp.data.database.AppDatabase
class MyApplication : Application() {
    companion object {
        val database: AppDatabase by lazy {
            Room.databaseBuilder(
                instance.applicationContext,
                AppDatabase::class.java,
                "my_database"
            ).build()
        }

        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
