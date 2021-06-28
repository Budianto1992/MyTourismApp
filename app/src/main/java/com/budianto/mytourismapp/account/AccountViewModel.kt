package com.budianto.mytourismapp.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budianto.mytourismapp.core.data.source.server.response.BaseResponse
import com.budianto.mytourismapp.core.data.source.server.response.ResponseLogin
import com.budianto.mytourismapp.core.data.source.server.response.ResponseRegister
import com.budianto.mytourismapp.core.domain.usecase.account.AccountUseCase
import com.google.gson.JsonObject
import retrofit2.Response

class AccountViewModel(private var accountUseCase: AccountUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    internal val isLoading: LiveData<Boolean> = _isLoading
    val throwableLive = MutableLiveData<Throwable>()

    var liveRegister = MutableLiveData<Response<BaseResponse<ResponseRegister>>>()
    internal fun register(jsonObject: JsonObject){
        accountUseCase.register(jsonObject, {
            liveRegister.postValue(it)
        }, {

        })
    }

    var liveLogin = MutableLiveData<Response<ResponseLogin>>()
    fun login(jsonObject: JsonObject){
        _isLoading.value = true
        accountUseCase.login(jsonObject, {
            liveLogin.postValue(it)
            _isLoading.value = false
        }, {
            _isLoading.value = false
            throwableLive.postValue(it)
        })
    }

}