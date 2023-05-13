package com.khaki.kaimono.compose.uimodel

data class TaskUiModel(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
)