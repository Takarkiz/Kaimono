package com.khaki.kaimono.screen.task_list.usecase

import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.db.TaskEntity
import com.khaki.kaimono.repository.LocationRepository
import com.khaki.kaimono.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskListInteractor(
    private val taskRepository: TaskRepository,
    private val locationRepository: LocationRepository,
) : TaskListUseCase {
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

    override val locations: Flow<List<TaskUiModel.Location>>
        get() {
            return locationRepository.locations.map { locations ->
                return@map locations
                    .sortedBy { it.order }
                    .map {
                    TaskUiModel.Location(
                        id = it.id,
                        name = it.name,
                    )
                }
            }
        }

    override suspend fun updateTaskStatus(taskId: Int) {
        val task = taskRepository.findById(taskId)
        taskRepository.update(task.copy(isDone = !task.isDone))
    }

    override suspend fun insertTask(task: TaskUiModel) {
        val entity = createTask(
            title = task.title,
            subTitle = task.description,
            isImportant = null,
            locationId = task.location?.id,
            dueDate = null,
        )
        taskRepository.insert(entity)
    }

    override suspend fun updateTask(task: TaskUiModel) {
        val prevTask = taskRepository.findById(task.id)
        taskRepository.update(
            prevTask.copy(
                title = task.title,
                subTitle = task.description,
                isDone = task.isDone,
                locationId = task.location?.id,
            )
        )
    }

    override suspend fun deleteTask(taskId: Int) {
        val task = taskRepository.findById(taskId)
        taskRepository.delete(task)
    }

    private fun createTask(
        title: String,
        subTitle: String?,
        isImportant: Boolean?,
        locationId: Int?,
        dueDate: String?
    ) = TaskEntity(
        uid = (0..Int.MAX_VALUE).random(),
        title = title,
        subTitle = subTitle,
        isDone = false,
        isImportant = isImportant,
        locationId = locationId,
        dueDate = dueDate,
        createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    )

}