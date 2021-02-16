package com.budianto.mytourismapp.core.data.source.server.network.apiretrofit

import com.budianto.mytourismapp.core.data.source.server.network.response.ListTourismResponse
import retrofit2.http.GET

interface TourismApi {

    @GET("list")
    suspend fun getAllTourism(): ListTourismResponse

}