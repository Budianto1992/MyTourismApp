package com.budianto.mytourismapp.core.data.repository

import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.data.source.local.LocalDataSource
import com.budianto.mytourismapp.core.data.source.server.LoginDataSource
import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.LoginResponse
import com.budianto.mytourismapp.core.domain.repository.ILoginRepository

class LoginRepository (private val dataSource: LoginDataSource,
                       private val localDataSource: LocalDataSource
                       ): ILoginRepository{
    override suspend fun login(request: LoginRequest): Resource<LoginResponse> {
        return try {
            val loginResponse = dataSource.login(request)
            localDataSource.deleteAll()
            Resource.Success(loginResponse)
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }
}