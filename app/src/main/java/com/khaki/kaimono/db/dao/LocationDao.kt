package com.khaki.kaimono.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.khaki.kaimono.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM LocationEntity")
    fun getAll(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM LocationEntity WHERE id IN (:locationId)")
    suspend fun loadAllByIds(locationId: Int): LocationEntity

    @Insert
    suspend fun insertAll(vararg locations: LocationEntity)

    @Update
    suspend fun update(location: LocationEntity)

    @Query("DELETE FROM LocationEntity WHERE id = :locationId")
    suspend fun delete(locationId: Int)
}