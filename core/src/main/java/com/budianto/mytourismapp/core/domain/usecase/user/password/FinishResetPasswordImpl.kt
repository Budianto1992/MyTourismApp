package com.budianto.mytourismapp.core.domain.usecase.user.password

import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class FinishResetPasswordImpl(private val repository: IAccountRepository): FinishResetPasswordUseCase {

    override suspend fun execute(request: ResetPasswordRequest): AccountState? {
        return when (val result = repository.finishPasswordReset(request)) {
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailureResponse(it)
            }
        }
    }

    override fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful reset user password: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    override fun manageFailureResponse(error: String): AccountState {
        Timber.e("Invalid reset user password: %s", error)
        return AccountState.INVALID_UPDATE
    }
}