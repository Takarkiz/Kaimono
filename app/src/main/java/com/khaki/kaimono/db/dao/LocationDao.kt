package com.khaki.kaimono.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.khaki.kaimono.db.entity.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAll(): Flow<List<Location>>

    @Query("SELECT * FROM location WHERE id IN (:locationId)")
    suspend fun loadAllByIds(locationId: Int): Location

    @Insert
    suspend fun insertAll(vararg locations: Location)

    @Update
    suspend fun update(location: Location)

    @Query("DELETE FROM location WHERE id = :locationId")
    suspend fun delete(locationId: Int)
}