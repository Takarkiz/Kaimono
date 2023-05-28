package com.khaki.kaimono.screen.task_list.usecase

import com.khaki.kaimono.compose.uimodel.TaskUiModel
import kotlinx.coroutines.flow.Flow

interface TaskListUseCase {

    val tasks: Flow<List<TaskUiModel>>

    suspend fun updateTaskStatus(taskId: Int)

    suspend fun insertTask(task: TaskUiModel)

    suspend fun updateTask(task: TaskUiModel)

    suspend fun deleteTask(taskId: Int)
}