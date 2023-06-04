package com.khaki.kaimono.repositoryImpl

import com.khaki.kaimono.db.TaskEntity
import com.khaki.kaimono.db.dao.TaskDao
import com.khaki.kaimono.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {

    override val tasks: Flow<List<TaskEntity>> = taskDao.getAll()

    override suspend fun findById(id: Int): TaskEntity {
        return taskDao.loadAllByIds(intArrayOf(id))[0]
    }

    override suspend fun findByTitle(title: String): TaskEntity {
        return taskDao.findByTitle(title)
    }

    override suspend fun insert(task: TaskEntity) {
        taskDao.insertAll(task)
    }

    override suspend fun update(task: TaskEntity) {
        taskDao.update(task)
    }

    override suspend fun delete(task: TaskEntity) {
        taskDao.delete(task)
    }

}