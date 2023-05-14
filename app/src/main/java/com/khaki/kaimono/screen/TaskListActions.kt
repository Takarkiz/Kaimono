package com.khaki.kaimono.screen

import com.khaki.kaimono.compose.uimodel.TaskUiModel

sealed interface TaskListActions {

    object DidTapOpenDialog : TaskListActions

    data class DidTapTask(
        val id: Int,
    ) : TaskListActions

    data class DidTapAddTask(
        val task: TaskUiModel,
    ) : TaskListActions

    data class DidTapStartEditTask(
        val id: Int,
    ) : TaskListActions

    data class DidTapEditTask(
        val task: TaskUiModel,
    ) : TaskListActions

    data class DidTapDeleteTask(
        val id: Int,
    ) : TaskListActions
}