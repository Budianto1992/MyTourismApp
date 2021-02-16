package com.budianto.mytourismapp.core.domain.usecase.user.login

import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.data.shared.service.SessionManager
import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.LoginResponse
import com.budianto.mytourismapp.core.domain.model.state.AuthenticationState
import com.budianto.mytourismapp.core.domain.repository.ILoginRepository
import timber.log.Timber

class LoginInteractor(private val userRepository: ILoginRepository,
                      private val sessionManager: SessionManager
                      ): LoginUseCase {
    override suspend fun execute(request: LoginRequest): AuthenticationState? {
        return when(val result = userRepository.login(request)){
            is Resource.Success -> handleSuccessResponse(result.data!!)
            is Resource.Loading -> null
            is Resource.Error -> result.message?.let { handleFailedResponse(it) }
        }
    }

    override fun handleSuccessResponse(loginResponse: LoginResponse): AuthenticationState {
        saveToken(loginResponse.authToken)
        Timber.d("Successful authentication: %s", loginResponse.statusCode)
        return AuthenticationState.AUTHENTICATED
    }

    override fun handleFailedResponse(error: String): AuthenticationState {
        Timber.e("Invalid Authentication: %s", error)
        return AuthenticationState.INVALID_AUTHENTICATION
    }

    override fun saveToken(token: String) {
        sessionManager.saveAuthToken(token)
    }

}