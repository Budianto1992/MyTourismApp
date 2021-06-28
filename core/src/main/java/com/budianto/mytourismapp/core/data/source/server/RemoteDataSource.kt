package com.budianto.mytourismapp.core.data.source.server

import android.util.Log
import com.budianto.mytourismapp.core.data.source.server.network.ApiResponse
import com.budianto.mytourismapp.core.data.source.server.network.WebService
import com.budianto.mytourismapp.core.data.source.server.response.TourismResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val webService: WebService){

    suspend fun getAllTourism(): Flow<ApiResponse<List<TourismResponse>>>{
        return flow {
            try {
                val response = webService.getAllTourism()
                val listTourim = response.places
                if (listTourim.isNotEmpty()){
                    emit(ApiResponse.Success(response.places))
                } else{
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}