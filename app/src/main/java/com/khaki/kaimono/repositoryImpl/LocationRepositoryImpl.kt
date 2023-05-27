package com.khaki.kaimono.repositoryImpl

import com.khaki.kaimono.db.dao.LocationDao
import com.khaki.kaimono.db.entity.LocationEntity

class LocationRepositoryImpl(
    private val locationDao: LocationDao,
) {

    val locations = locationDao.getAll()

    suspend fun findById(id: Int): LocationEntity {
        return locationDao.loadAllByIds(id)
    }

    suspend fun insert(location: LocationEntity) {
        locationDao.insertAll(location)
    }

    suspend fun update(location: LocationEntity) {
        locationDao.update(location)
    }

    suspend fun delete(locationId: Int) {
        locationDao.delete(locationId)
    }

}