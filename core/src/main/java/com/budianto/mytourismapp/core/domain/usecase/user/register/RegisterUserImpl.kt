package com.budianto.mytourismapp.core.domain.usecase.user.register

import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.model.state.RegistrationState
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class RegisterUserImpl(private val repository: IAccountRepository): RegisterUserUseCase {
    override suspend fun execute(user: User): RegistrationState? {
        return when(val result = repository.register(user)){
            is Result.Success -> manageSeccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailureResponse(it)
            }
        }
    }

    override fun manageSeccessResponse(response: String): RegistrationState {
        Timber.d("Successful register: %s", response)
        return RegistrationState.REGISTRATION_COMPLETED
    }

    override fun manageFailureResponse(error: String): RegistrationState {
        Timber.e("Invalid register: %s", error)
        return RegistrationState.INVALID_REGISTRATION
    }
}