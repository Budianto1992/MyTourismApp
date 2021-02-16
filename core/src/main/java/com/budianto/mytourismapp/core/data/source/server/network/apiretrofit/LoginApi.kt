package com.budianto.mytourismapp.core.data.source.server.network.apiretrofit

import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("authenticate")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}