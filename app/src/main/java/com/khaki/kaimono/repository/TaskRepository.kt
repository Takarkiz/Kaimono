package com.khaki.kaimono.repository

import com.khaki.kaimono.db.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    val tasks: Flow<List<Task>>

    suspend fun findById(id: Int): Task

    suspend fun findByTitle(title: String): Task

    suspend fun insert(task: Task)

    suspend fun update(task: Task)

    suspend fun delete(task: Task)

}