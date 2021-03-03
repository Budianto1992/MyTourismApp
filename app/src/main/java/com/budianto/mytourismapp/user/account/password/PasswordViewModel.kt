package com.budianto.mytourismapp.user.account.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.core.domain.usecase.user.password.FinishResetPasswordUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.password.StartResetPasswordUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.password.UpdatePasswordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordViewModel(private val updatePassword: UpdatePasswordUseCase,
                        private val startResetPassword: StartResetPasswordUseCase,
                        private val finishResetPassword: FinishResetPasswordUseCase
): ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val updatePasswordState = MutableLiveData(AccountState.UPDATING)
    val startResetPasswordState = MutableLiveData(AccountState.UPDATING)
    val finishResetPasswordState = MutableLiveData(AccountState.UPDATING)

    fun updatePassword(currentPassword: String, newPassword: String){
        val passwordRequest = PasswordRequest(currentPassword, newPassword)
        coroutineScope.launch {
            val updatePasswordResponse = updatePassword.execute(passwordRequest)
            updatePasswordState.postValue(updatePasswordResponse)
        }
    }

    fun startResetPassword(email: String){
        coroutineScope.launch {
            val startResetPasswordResponse = startResetPassword.execute(email)
            startResetPasswordState.postValue(startResetPasswordResponse)
        }
    }

    fun finishResetPassword(key: String, newPassword: String){
        val resetPasswordRequest = ResetPasswordRequest(key, newPassword)
        coroutineScope.launch {
            val resetPasswordRequestResponse = finishResetPassword.execute(resetPasswordRequest)
            finishResetPasswordState.postValue(resetPasswordRequestResponse)
        }
    }
}