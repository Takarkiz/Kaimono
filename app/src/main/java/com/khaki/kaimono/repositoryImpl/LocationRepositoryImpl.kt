package com.khaki.kaimono.repositoryImpl

import com.khaki.kaimono.db.dao.LocationDao
import com.khaki.kaimono.db.entity.LocationEntity
import com.khaki.kaimono.repository.LocationRepository

class LocationRepositoryImpl(
    private val locationDao: LocationDao,
) : LocationRepository {

    override val locations = locationDao.getAll()

    override suspend fun findById(id: Int): LocationEntity {
        return locationDao.loadAllByIds(id)
    }

    override suspend fun insert(location: LocationEntity) {
        locationDao.insertAll(location)
    }

    override suspend fun update(location: LocationEntity) {
        locationDao.update(location)
    }

    override suspend fun delete(locationId: Int) {
        locationDao.delete(locationId)
    }

}