package com.khaki.kaimono.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.khaki.kaimono.db.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE uid IN (:taskIds)")
    suspend fun loadAllByIds(taskIds: IntArray): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE title LIKE :title LIMIT 1")
    suspend fun findByTitle(title: String): TaskEntity

    @Insert
    suspend fun insertAll(vararg tasks: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)
}