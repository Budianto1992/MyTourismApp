package com.budianto.mytourismapp.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.domain.model.state.AuthenticationState
import com.budianto.mytourismapp.core.domain.usecase.user.login.LoginUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.logout.LogoutUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class LoginViewModel(private val loginUser: LoginUseCase,
                     private val logoutUser: LogoutUserUseCase
                     ) : ViewModel() {

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    val authenticationState = MutableLiveData<AuthenticationState>()
    val logoutState = MutableLiveData<LogoutState>()

    fun refuseAuthentication(){
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
    }

    fun authentication(username: String, password: String, isRemamber: Boolean){
        val loginRequest = LoginRequest(username, password, isRemamber)
        coroutineScope.launch {
            val authenticationResponse = loginUser.execute(loginRequest)
            authenticationState.postValue(authenticationResponse)
        }
    }

    fun signOut(){
        logoutUser.execute()
        logoutState.value = LogoutState.LOGOUT_COMPLATE
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }
}