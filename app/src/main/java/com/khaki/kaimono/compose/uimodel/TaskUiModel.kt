package com.khaki.kaimono.compose.uimodel

import com.khaki.kaimono.db.Task

data class TaskUiModel(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val location: String? = null,
) {
    constructor(task: Task) : this(
        id = task.uid,
        title = task.title,
        description = task.subTitle,
        isDone = task.isDone,
        location = task.location,
    )
}