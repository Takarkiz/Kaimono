package com.khaki.kaimono.repositoryImpl

import com.khaki.kaimono.db.dao.LocationDao
import com.khaki.kaimono.db.entity.Location

class LocationRepositoryImpl(
    private val locationDao: LocationDao,
) {

    val locations = locationDao.getAll()

    suspend fun findById(id: Int): Location {
        return locationDao.loadAllByIds(id)
    }

    suspend fun insert(location: Location) {
        locationDao.insertAll(location)
    }

    suspend fun update(location: Location) {
        locationDao.update(location)
    }

    suspend fun delete(locationId: Int) {
        locationDao.delete(locationId)
    }

}