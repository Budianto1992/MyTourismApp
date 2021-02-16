package com.budianto.mytourismapp.core.domain.usecase.user.password

import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.domain.model.state.AccountState

interface FinishResetPasswordUseCase {
    suspend fun execute(request: ResetPasswordRequest): AccountState?
    fun manageSuccessResponse(response: String): AccountState
    fun manageFailureResponse(error: String): AccountState
}