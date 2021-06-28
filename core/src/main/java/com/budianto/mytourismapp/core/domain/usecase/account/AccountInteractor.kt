package com.budianto.mytourismapp.core.domain.usecase.account

import com.budianto.mytourismapp.core.data.source.server.response.BaseResponse
import com.budianto.mytourismapp.core.data.source.server.response.ResponseLogin
import com.budianto.mytourismapp.core.data.source.server.response.ResponseRegister
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import com.google.gson.JsonObject
import retrofit2.Response

class AccountInteractor(private val accountRepository: IAccountRepository): AccountUseCase {
    override fun register(
        jsonObject: JsonObject,
        onResult: (Response<BaseResponse<ResponseRegister>>?) -> Unit,
        onError: (Throwable) -> Unit
    ) = accountRepository.register(jsonObject, onResult, onError)

    override fun login(
        jsonObject: JsonObject,
        onResult: (Response<ResponseLogin>?) -> Unit,
        onError: (Throwable) -> Unit
    ) = accountRepository.login(jsonObject, onResult, onError)


}