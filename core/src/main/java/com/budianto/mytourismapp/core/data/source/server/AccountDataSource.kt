package com.budianto.mytourismapp.core.data.source.server

import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.RegisterRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.UserRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.UserResponse
import okio.IOException

class AccountDataSource (private val dataSourceProvider: DataSourceProvider){

    suspend fun register(request: RegisterRequest){
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.register(request)
    }

    suspend fun activate(key: String){
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.activate(key)
    }

    suspend fun get(): UserResponse{
        val accountApi = dataSourceProvider.getAccountDataSource()
        val response = accountApi.get()
        if (!response.isSuccessful) throw IOException(response.message())
        return response.body() ?: throw IllegalStateException("Empty response body")
    }

    suspend fun save(request: UserRequest){
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.saveAccount(request)
    }

    suspend fun changePassword(request: PasswordRequest){
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.changePassword(request)
    }

    suspend fun requestPasswordReset(mail: String) {
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.requestPasswordReset(mail)
    }

    suspend fun finishPasswordReset(request: ResetPasswordRequest) {
        val accountApi = dataSourceProvider.getAccountDataSource()
        accountApi.finishPasswordReset(request)
    }
}