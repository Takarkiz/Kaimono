package com.khaki.kaimono.screen

import com.khaki.kaimono.compose.uimodel.TaskUiModel

data class TaskListUiState(
    val tasks: List<TaskUiModel> = listOf(),
    val isLoading: Boolean = false,
)

