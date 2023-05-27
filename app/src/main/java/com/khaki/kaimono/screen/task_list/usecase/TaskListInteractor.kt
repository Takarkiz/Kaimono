package com.khaki.kaimono.screen.task_list.usecase

import com.khaki.kaimono.repository.LocationRepository
import com.khaki.kaimono.repository.TaskRepository

class TaskListInteractor(
    private val taskRepository: TaskRepository,
    private val locationRepository: LocationRepository,
): TaskListUseCase {

}