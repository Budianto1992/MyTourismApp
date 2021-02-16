package com.budianto.mytourismapp.core.data.source.local

import com.budianto.mytourismapp.core.data.source.local.entity.TourismEntity
import com.budianto.mytourismapp.core.data.source.local.room.TourismDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val tourismDao: TourismDao) {


    fun getAllTourism(): Flow<List<TourismEntity>> = tourismDao.getAllTourism()

    fun getFavoriteTourism(): Flow<List<TourismEntity>> = tourismDao.getFavoriteTourism()

    suspend fun insertTourism(tourismEntity: List<TourismEntity>) = tourismDao.insertTourism(tourismEntity)

    fun updateTourism(tourismEntity: TourismEntity, newState: Boolean){
        tourismEntity.isFavorite = newState
        tourismDao.updateTourism(tourismEntity)
    }

    suspend fun deleteAll(){
        tourismDao.deleteAll()
    }
}