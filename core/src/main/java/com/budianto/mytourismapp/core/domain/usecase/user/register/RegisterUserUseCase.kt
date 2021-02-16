package com.budianto.mytourismapp.core.domain.usecase.user.register

import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.model.state.RegistrationState

interface RegisterUserUseCase {
    suspend fun execute(user: User): RegistrationState?
    fun manageSeccessResponse(response: String): RegistrationState
    fun manageFailureResponse(error: String): RegistrationState
}