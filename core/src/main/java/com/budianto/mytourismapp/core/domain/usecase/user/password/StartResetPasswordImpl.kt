package com.budianto.mytourismapp.core.domain.usecase.user.password

import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class StartResetPasswordImpl(private val repository: IAccountRepository): StartResetPasswordUseCase {

    override suspend fun execute(mail: String): AccountState? =
            when(val result = repository.requestPasswordReset(mail)){
                is Result.Success -> manageSuccessResponse(result.data)
                is Result.Error -> result.throwable.message?.let {
                    manageFailedResponse(it)
                }
            }

    override fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful start to reset user password with email: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    override fun manageFailedResponse(error: String): AccountState {
        Timber.e("Invalid start to reset user password with email: %s", error)
        return AccountState.INVALID_UPDATE
    }

}