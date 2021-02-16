
package com.budianto.mytourismapp.core.data.source.server

import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.ApiResponse
import com.budianto.mytourismapp.core.data.source.server.network.request.TourismRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class TourismRemoteDataSource (private val dataSourceProvider: DataSourceProvider){

    fun getTourismDataSource(): Flow<ApiResponse<List<TourismRequest>>>{
        return flow {
            try {
                val response = dataSourceProvider.getAllTourism().getAllTourism()
                val listTourim = response.places
                if (listTourim.isNotEmpty()){
                    emit(ApiResponse.Success(response.places))
                } else{
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e(e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}