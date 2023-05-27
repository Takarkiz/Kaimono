package com.khaki.kaimono.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khaki.kaimono.db.Task
import com.khaki.kaimono.db.dao.LocationDao
import com.khaki.kaimono.db.dao.TaskDao
import com.khaki.kaimono.db.entity.Location

@Database(entities = [Task::class, Location::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun locationDao(): LocationDao
}