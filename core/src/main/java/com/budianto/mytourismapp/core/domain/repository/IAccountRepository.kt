package com.budianto.mytourismapp.core.domain.repository

import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.User

interface IAccountRepository {
    suspend fun register(user: User): Result<String>
    suspend fun activate(key: String): Result<String>
    suspend fun get(): Result<User>
    suspend fun save(user: User): Result<String>
    suspend fun changePassword(passwordRequest: PasswordRequest): Result<String>
    suspend fun requestPasswordReset(mail: String): Result<String>
    suspend fun finishPasswordReset(resetPasswordRequest: ResetPasswordRequest): Result<String>
}