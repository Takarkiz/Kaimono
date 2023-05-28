package com.khaki.kaimono.screen.task_list.usecase

import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.repository.LocationRepository
import com.khaki.kaimono.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class TaskListInteractor(
    private val taskRepository: TaskRepository,
    private val locationRepository: LocationRepository,
): TaskListUseCase {
    override val tasks: Flow<List<TaskUiModel>>
        get() {
            return taskRepository.tasks
                .combine(locationRepository.locations) { tasks, locations ->
                    return@combine tasks.map { task ->
                        val location = locations.find { it.id == task.locationId }
                        TaskUiModel.of(task, location)
                    }
                }
        }

    override suspend fun updateTaskStatus(taskId: Int) {
        val task = taskRepository.findById(taskId)
        taskRepository.update(task.copy(isDone = !task.isDone))
    }

    override suspend fun insertTask(task: TaskUiModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: TaskUiModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

}