package com.budianto.mytourismapp.core.domain.usecase.user.getuser

import com.budianto.mytourismapp.core.domain.Result
import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import timber.log.Timber

class GetUserImpl(private val repository: IAccountRepository): GetUserUseCase {
    override suspend fun execute(): User? {
        return when(val result = repository.get()){
            is Result.Success -> manageSuccessResponse(result.data)
            is Result.Error -> result.throwable.message?.let {
                manageFailureResponse(it)
            }
        }
    }

    override fun manageSuccessResponse(result: User): User {
        Timber.d("Successful get user: %s", result)
        return result
    }

    override fun manageFailureResponse(error: String): User {
        Timber.e("Invalid get user: %s", error)
        return User()
    }
}