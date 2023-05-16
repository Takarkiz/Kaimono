package com.khaki.kaimono.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey val uid: Int,
    val title: String,
    @ColumnInfo(name = "sub_title") val subTitle: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "is_important") val isImportant: Boolean?,
    val location: String?,
    @ColumnInfo(name = "due_date") val dueDate: String?,
    @ColumnInfo(name = "created_at") val createdAt: String,
)