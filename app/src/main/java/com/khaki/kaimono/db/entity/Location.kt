package com.khaki.kaimono.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey val id: Int,
    val name: String,
    val order: Int,
)