package com.khaki.kaimono.screen

import com.khaki.kaimono.compose.uimodel.TaskUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TaskListUiState(
    val tasks: Flow<List<TaskUiModel>> = flowOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

