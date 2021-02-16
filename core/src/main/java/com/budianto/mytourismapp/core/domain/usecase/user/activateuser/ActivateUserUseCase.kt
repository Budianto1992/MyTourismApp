package com.budianto.mytourismapp.core.domain.usecase.user.activateuser

import com.budianto.mytourismapp.core.domain.model.state.RegistrationState

interface ActivateUserUseCase {
    suspend fun execute(activateKey: String): RegistrationState?
    fun manageSuccessResponse(response: String): RegistrationState
    fun manageFailureResponse(error: String): RegistrationState
}