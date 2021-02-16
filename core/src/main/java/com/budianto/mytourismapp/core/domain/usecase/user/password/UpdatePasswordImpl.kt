package com.budianto.mytourismapp.core.domain.usecase.user.password

import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class UpdatePasswordImpl (private val repository: IAccountRepository): UpdatePasswordUseCase {
    override suspend fun execute(request: PasswordRequest): AccountState? {
        return when (val result = repository.changePassword(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailureResponse(it)
            }
        }
    }

    override fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful update user password: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    override fun manageFailureResponse(error: String): AccountState {
        Timber.e("Invalid update user password: %s", error)
        return AccountState.INVALID_UPDATE
    }
}