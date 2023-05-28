package com.khaki.kaimono.compose.uimodel

import com.khaki.kaimono.db.TaskEntity
import com.khaki.kaimono.db.entity.LocationEntity

data class TaskUiModel(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val location: Location?,
) {

    data class Location(
        val id: Int,
        val name: String,
    )

    companion object {

        fun of(task: TaskEntity, location: LocationEntity?): TaskUiModel {
            return TaskUiModel(
                id = task.uid,
                title = task.title,
                description = task.subTitle,
                isDone = task.isDone,
                location = location?.let {
                    Location(
                        id = it.id,
                        name = it.name,
                    )
                }
            )
        }
    }
}