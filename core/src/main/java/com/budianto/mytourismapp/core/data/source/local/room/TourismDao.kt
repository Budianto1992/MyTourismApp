package com.budianto.mytourismapp.core.data.source.local.room

import androidx.room.*
import com.budianto.mytourismapp.core.data.source.local.entity.TourismEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TourismDao {

    @Query("SELECT * FROM tourismentities")
    fun getAllTourism(): Flow<List<TourismEntity>>

    @Query("SELECT * FROM tourismentities where isFavorite = 1")
    fun getFavoriteTourism(): Flow<List<TourismEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourism(tourisEntity: List<TourismEntity>)

    @Update
    fun updateTourism(tourismEntity: TourismEntity)
}