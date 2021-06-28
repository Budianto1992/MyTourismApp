package com.budianto.mytourismapp.core.data.source.server.network

import com.budianto.mytourismapp.core.data.source.server.response.ListTourismResponse
import retrofit2.http.GET

interface WebService {

    @GET("list")
    suspend fun getAllTourism(): ListTourismResponse
}