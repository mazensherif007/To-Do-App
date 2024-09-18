package com.example.todo

import android.app.Application
import com.example.todo.app.AppDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
    }
}