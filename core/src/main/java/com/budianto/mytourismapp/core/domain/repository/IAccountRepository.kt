package com.budianto.mytourismapp.core.domain.repository

import com.budianto.mytourismapp.core.data.source.server.response.BaseResponse
import com.budianto.mytourismapp.core.data.source.server.response.ResponseLogin
import com.budianto.mytourismapp.core.data.source.server.response.ResponseRegister
import com.google.gson.JsonObject
import retrofit2.Response

interface IAccountRepository {

    fun register(
        jsonObject: JsonObject,
        onResult: (Response<BaseResponse<ResponseRegister>>?) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun login(
        jsonObject: JsonObject,
        onResult: (Response<ResponseLogin>?) -> Unit,
        onError: (Throwable) -> Unit
    )
}