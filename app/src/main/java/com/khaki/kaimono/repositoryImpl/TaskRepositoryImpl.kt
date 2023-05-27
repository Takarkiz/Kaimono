package com.khaki.kaimono.repositoryImpl

import com.khaki.kaimono.db.Task
import com.khaki.kaimono.db.dao.TaskDao
import com.khaki.kaimono.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {

    override val tasks: Flow<List<Task>> = taskDao.getAll()

    override suspend fun findById(id: Int): Task {
        return taskDao.loadAllByIds(intArrayOf(id))[0]
    }

    override suspend fun findByTitle(title: String): Task {
        return taskDao.findByTitle(title)
    }

    override suspend fun insert(task: Task) {
        taskDao.insertAll(task)
    }

    override suspend fun update(task: Task) {
        taskDao.update(task)
    }

    override suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

}