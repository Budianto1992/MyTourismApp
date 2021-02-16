package com.budianto.mytourismapp.core.domain.usecase.user.password

import com.budianto.mytourismapp.core.domain.model.state.AccountState
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

interface StartResetPasswordUseCase {
    suspend fun execute(mail: String): AccountState?

    fun manageSuccessResponse(response: String): AccountState

    fun manageFailedResponse(error: String): AccountState
}