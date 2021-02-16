package com.budianto.mytourismapp.core.data.repository

import com.budianto.mytourismapp.core.data.NetworkBoundResource
import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.data.source.local.LocalDataSource
import com.budianto.mytourismapp.core.data.source.server.TourismRemoteDataSource
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.ApiResponse
import com.budianto.mytourismapp.core.data.source.server.network.request.TourismRequest
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.core.domain.repository.ITourismRepository
import com.budianto.mytourismapp.core.util.AppExecutors
import com.budianto.mytourismapp.core.util.DataMapping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TourismRepository(
        private val localDataSource: LocalDataSource,
        private val tourismRemoteDataSource: TourismRemoteDataSource,
        private val appExecutors: AppExecutors
): ITourismRepository {

    override fun getAllTourism(): Flow<Resource<List<Tourism>>> {
        return object : NetworkBoundResource<List<Tourism>, List<TourismRequest>>(){
            override fun loadFromDb(): Flow<List<Tourism>> {
                return localDataSource.getAllTourism().map {
                    DataMapping.mapEntitiesToDomain(it)
                }
            }
            override fun shouldFetch(data: List<Tourism>?): Boolean =
                    data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TourismRequest>>> = tourismRemoteDataSource.getTourismDataSource()

            override suspend fun saveCallResult(data: List<TourismRequest>) {
                val tourismList = DataMapping.mapResponseToEntites(data)
                localDataSource.insertTourism(tourismList)
            }

        }.asFlow()
    }

    override fun getFavoriteTourism(): Flow<List<Tourism>> {
        return localDataSource.getFavoriteTourism().map {
            DataMapping.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapping.mapDomainToEntites(tourism)
        appExecutors.diskIO().execute { localDataSource.updateTourism(tourismEntity, state) }
    }
}