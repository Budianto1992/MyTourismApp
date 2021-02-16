package com.budianto.mytourismapp.core.domain.usecase.user.getuser

import com.budianto.mytourismapp.core.domain.model.User

interface GetUserUseCase {
    suspend fun execute(): User?
    fun manageSuccessResponse(result: User): User
    fun manageFailureResponse(error: String): User
}