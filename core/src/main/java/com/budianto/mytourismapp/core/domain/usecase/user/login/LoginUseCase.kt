package com.budianto.mytourismapp.core.domain.usecase.user.login

import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.LoginResponse
import com.budianto.mytourismapp.core.domain.model.state.AuthenticationState

interface LoginUseCase {

    suspend fun execute(request: LoginRequest): AuthenticationState?
    fun handleSuccessResponse(loginResponse: LoginResponse): AuthenticationState
    fun handleFailedResponse(error: String): AuthenticationState
    fun saveToken(token: String)

}