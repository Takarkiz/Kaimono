package com.khaki.kaimono.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khaki.kaimono.db.Task
import com.khaki.kaimono.db.dao.TaskDao

@Database(entities = [Task::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}