package com.khaki.kaimono.screen.task_list

import com.khaki.kaimono.compose.uimodel.TaskUiModel

sealed interface TaskListActions {

    object DidTapFAB : TaskListActions

    object DismissDialog : TaskListActions

    data class DidTapTask(
        val id: Int,
    ) : TaskListActions

    object DidTapConfirmTask : TaskListActions

    data class DidTapStartEditTask(
        val id: Int,
    ) : TaskListActions

    object CancelEditTask : TaskListActions

    data class InputEditingTask(
        val task: TaskUiModel,
    ) : TaskListActions

    data class DidTapDeleteTask(
        val id: Int,
    ) : TaskListActions
}