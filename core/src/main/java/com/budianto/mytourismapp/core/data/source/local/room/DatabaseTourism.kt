package com.budianto.mytourismapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budianto.mytourismapp.core.data.source.local.entity.TourismEntity


@Database(entities = [TourismEntity::class], version = 2, exportSchema = false)
abstract class DatabaseTourism : RoomDatabase(){

    abstract fun tourismDao(): TourismDao
}