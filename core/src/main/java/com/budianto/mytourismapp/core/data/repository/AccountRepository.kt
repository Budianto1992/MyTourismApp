package com.budianto.mytourismapp.core.data.repository

import com.budianto.mytourismapp.core.data.source.server.AccountDataSource
import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.toRegisterRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.toRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.toDomain
import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository

private const val SUCCESSFUL_RESPONSE = "OK"

class AccountRepository(private val dataSource: AccountDataSource): IAccountRepository {
    override suspend fun register(user: User): Result<String> {
        return try {
                dataSource.register(user.toRegisterRequest())
                Result.Success(SUCCESSFUL_RESPONSE)
            } catch (ex: Exception){
                Result.Error(ex)
            }
    }

    override suspend fun activate(key: String): Result<String> {
        return try {
                dataSource.activate(key)
                Result.Success(SUCCESSFUL_RESPONSE)
            } catch (ex: Exception){
                Result.Error(ex)
            }
    }

    override suspend fun get(): Result<User> {
        return try {
                val userResponse = dataSource.get()
                Result.Success(userResponse.toDomain())
            } catch (ex: Exception){
                Result.Error(ex)
            }
    }

    override suspend fun save(user: User): Result<String> {
        return try {
                dataSource.save(user.toRequest())
                Result.Success(SUCCESSFUL_RESPONSE)
            } catch (ex: Exception){
                Result.Error(ex)
            }

    }

    override suspend fun changePassword(passwordRequest: PasswordRequest): Result<String> {
        return try {
                dataSource.changePassword(passwordRequest)
                Result.Success(SUCCESSFUL_RESPONSE)
            } catch (ex: Exception) {
                Result.Error(ex)
            }

    }

    override suspend fun requestPasswordReset(mail: String): Result<String> {
        return try {
                dataSource.requestPasswordReset(mail)
                Result.Success(SUCCESSFUL_RESPONSE)
            } catch (ex: Exception) {
                Result.Error(ex)
            }
    }

    override suspend fun finishPasswordReset(resetPasswordRequest: ResetPasswordRequest): Result<String> {
        return try {
                dataSource.finishPasswordReset(resetPasswordRequest)
                Result.Success(SUCCESSFUL_RESPONSE)
            } catch (ex: Throwable) {
                Result.Error(ex)
            }
    }
}