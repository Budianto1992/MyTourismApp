package com.budianto.mytourismapp.core.domain.usecase.user.activateuser

import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.state.RegistrationState
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class ActivateUserImpl(private val repository: IAccountRepository): ActivateUserUseCase {
    override suspend fun execute(activateKey: String): RegistrationState? {
        return when(val result = repository.activate(activateKey)){
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailureResponse(it)
            }
        }
    }

    override fun manageSuccessResponse(response: String): RegistrationState {
        Timber.d("Successful activation: %s", response)
        return RegistrationState.ACTIVATION_COMPLETED
    }

    override fun manageFailureResponse(error: String): RegistrationState {
        Timber.e("Invalid activation: %s", error)
        return RegistrationState.INVALID_ACTIVATION
    }
}