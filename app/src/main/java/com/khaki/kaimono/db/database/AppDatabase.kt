package com.khaki.kaimono.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khaki.kaimono.db.TaskEntity
import com.khaki.kaimono.db.dao.LocationDao
import com.khaki.kaimono.db.dao.TaskDao
import com.khaki.kaimono.db.entity.LocationEntity

@Database(entities = [TaskEntity::class, LocationEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun locationDao(): LocationDao
}