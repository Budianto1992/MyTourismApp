package com.budianto.mytourismapp.core.domain.usecase.user.updateuser

import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.model.state.AccountState

interface UpdateUserUseCase {
    suspend fun execute(user: User): AccountState?
    fun manageSuccessResponse(response: String): AccountState
    fun manageFailureResponse(error: String): AccountState
}