package com.khaki.kaimono.repository

import com.khaki.kaimono.db.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    val tasks: Flow<List<TaskEntity>>

    suspend fun findById(id: Int): TaskEntity

    suspend fun findByTitle(title: String): TaskEntity

    suspend fun insert(task: TaskEntity)

    suspend fun update(task: TaskEntity)

    suspend fun delete(task: TaskEntity)

}