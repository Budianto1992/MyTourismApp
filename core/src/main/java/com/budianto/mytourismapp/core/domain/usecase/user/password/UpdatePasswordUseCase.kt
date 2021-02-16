package com.budianto.mytourismapp.core.domain.usecase.user.password

import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.domain.model.state.AccountState

interface UpdatePasswordUseCase {
    suspend fun execute(request: PasswordRequest): AccountState?
    fun manageSuccessResponse(response: String): AccountState
    fun manageFailureResponse(error: String): AccountState
}