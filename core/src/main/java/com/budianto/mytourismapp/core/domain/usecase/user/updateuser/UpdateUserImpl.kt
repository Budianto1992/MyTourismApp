package com.budianto.mytourismapp.core.domain.usecase.user.updateuser

import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class UpdateUserImpl(private val repository: IAccountRepository): UpdateUserUseCase {
    override suspend fun execute(user: User): AccountState? {
        return when(val result = repository.save(user)){
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailureResponse(it)
            }
        }
    }

    override fun manageSuccessResponse(response: String): AccountState {
        Timber.d("Successful update user data: %s", response)
        return AccountState.UPDATE_COMPLETED
    }

    override fun manageFailureResponse(error: String): AccountState {
        Timber.e("Invalid update user data: %s", error)
        return AccountState.INVALID_UPDATE
    }

}