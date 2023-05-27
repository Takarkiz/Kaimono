package com.khaki.kaimono.repository

import com.khaki.kaimono.db.Task
import com.khaki.kaimono.db.dao.TaskDao

class TaskRepository(
    private val taskDao: TaskDao
) {

    val tasks = taskDao.getAll()

    suspend fun findById(id: Int): Task {
        return taskDao.loadAllByIds(intArrayOf(id))[0]
    }

    suspend fun findByTitle(title: String): Task {
        return taskDao.findByTitle(title)
    }

    suspend fun insert(task: Task) {
        taskDao.insertAll(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

}