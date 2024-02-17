package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.dao.TasksDao
import com.example.todo.database.model.Task
import kotlin.coroutines.coroutineContext

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TasksDao
    companion object{
        const val DATABASE_NAME = "taskDatabase"
        private var database:MyDatabase?= null

        fun getInstance(context:Context):MyDatabase{
            if (database==null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

            }
         return database!!
        }


    }
}