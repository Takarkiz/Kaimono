package com.khaki.kaimono.screen.task_list

import com.khaki.kaimono.compose.uimodel.TaskUiModel

data class TaskListUiState(
    val tasks: List<TaskUiModel> = listOf(),
    val locations: List<TaskUiModel.Location> = listOf(),
    val isLoading: Boolean = false,
    val isOpenBottomSheet: Boolean = false,
    val editingTask: TaskUiModel? = null,
    val editingMode: Boolean = false,
)