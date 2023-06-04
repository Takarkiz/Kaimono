package com.khaki.kaimono.repository

import com.khaki.kaimono.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    val locations: Flow<List<LocationEntity>>

    suspend fun findById(id: Int): LocationEntity

    suspend fun insert(location: LocationEntity)

    suspend fun update(location: LocationEntity)

    suspend fun delete(locationId: Int)
}