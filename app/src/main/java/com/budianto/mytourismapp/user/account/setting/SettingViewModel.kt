package com.budianto.mytourismapp.user.account.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budianto.mytourismapp.core.domain.model.User
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.core.domain.usecase.user.getuser.GetUserUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.updateuser.UpdateUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(private val getUser: GetUserUseCase,
                       private val updateUser: UpdateUserUseCase
                       ) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val user = MutableLiveData<User>()
    val updateAccountState = MutableLiveData(AccountState.UPDATING)

    fun getAccount(){
        coroutineScope.launch {
            val userResult = getUser.execute()
            user.postValue(userResult)
        }
    }

    fun updateAccount(username: String, email: String, firstName: String, lastName: String){
        val updatedUser = User(username = username , email = email , firstName = firstName, lastName = lastName)

        coroutineScope.launch {
            val updateAccountResponse = updateUser.execute(updatedUser)
            updateAccountState.postValue(updateAccountResponse)
        }
    }
}