package com.khaki.kaimono.screen

import com.khaki.kaimono.compose.uimodel.TaskUiModel

sealed interface TaskListActions {

    object DidTapFAB : TaskListActions

    object DismissDialog : TaskListActions

    data class DidTapTask(
        val id: Int,
    ) : TaskListActions

    object DidTapAddTask: TaskListActions

    data class DidTapStartEditTask(
        val id: Int,
    ) : TaskListActions

    data class EditTask(
        val task: TaskUiModel,
    ) : TaskListActions

    data class DidTapEditTask(
        val task: TaskUiModel,
    ) : TaskListActions

    data class DidTapDeleteTask(
        val id: Int,
    ) : TaskListActions
}