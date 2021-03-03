package com.budianto.mytourismapp.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.model.state.RegistrationState
import com.budianto.mytourismapp.core.domain.usecase.user.activateuser.ActivateUserUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.register.RegisterUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUser: RegisterUserUseCase,
                        private val activateUser: ActivateUserUseCase): ViewModel(){

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val registrationState = MutableLiveData(RegistrationState.COLLECT_PROFILE_DATA)

    fun createAccount(username: String, email: String, password: String){
        val registeredUser = User(username = username, email = email, password = password)
        coroutineScope.launch {
            val registerUserResponse = registerUser.execute(registeredUser)
            registrationState.postValue(registerUserResponse)
        }
    }

    fun userCancelRegistration(): Boolean{
        //Clear existing registration data
        registrationState.value = RegistrationState.COLLECT_PROFILE_DATA
        return true
    }

    fun activateAccount(activateKey: String){
        coroutineScope.launch {
            val activationResponse = activateUser.execute(activateKey)
            registrationState.postValue(activationResponse)
        }
    }
}