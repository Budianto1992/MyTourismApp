package com.budianto.mytourismapp.core.data.source.server

import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.LoginResponse
import okio.IOException

class LoginDataSource(private val dataSourceProvider: DataSourceProvider) {

    suspend fun login(request: LoginRequest): LoginResponse{
        val loginApi = dataSourceProvider.getLoginDataSource()
        val response = loginApi.login(request)

        if (!response.isSuccessful) throw IOException(response.message())
        return response.body() ?: throw IllegalStateException("Empty response body")
    }
}