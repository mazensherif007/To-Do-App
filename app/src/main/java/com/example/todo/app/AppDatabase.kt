package com.example.todo.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.app.dao.TasksDao
import com.example.todo.app.model.Task

@Database(entities = [Task::class] , version = 2 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object {

        private var db: AppDatabase? = null
        private const val databaseName = "Tasks-database"

        fun getInstance(): AppDatabase{
            return db!!
        }

        fun initDatabase(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseName
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return db!!
        }
    }
}