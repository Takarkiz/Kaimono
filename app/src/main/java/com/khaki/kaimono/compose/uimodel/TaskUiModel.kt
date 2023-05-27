package com.khaki.kaimono.compose.uimodel

import com.khaki.kaimono.db.Task

data class TaskUiModel(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val location: String? = null,
) {

    companion object {
        fun fromEntity(task: Task) = TaskUiModel(
            id = task.uid,
            title = task.title,
            description = task.subTitle,
            isDone = task.isDone,
            location = task.locationId.toString()
        )
    }
}