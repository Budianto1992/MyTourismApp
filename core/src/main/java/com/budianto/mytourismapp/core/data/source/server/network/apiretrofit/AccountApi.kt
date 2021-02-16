package com.budianto.mytourismapp.core.data.source.server.network.apiretrofit

import com.budianto.mytourismapp.core.data.source.server.network.request.PasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.RegisterRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.ResetPasswordRequest
import com.budianto.mytourismapp.core.data.source.server.network.request.UserRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest)

    @GET("active")
    suspend fun activate(@Query("key") key: String)

    @GET("account")
    suspend fun get(): Response<UserResponse>

    @POST("account")
    suspend fun saveAccount(@Body userRequest: UserRequest)

    @POST("account/change-password")
    suspend fun changePassword(@Body passwordRequest: PasswordRequest)

    @POST("account/reset-password/init")
    suspend fun requestPasswordReset(@Body mail: String)

    @POST("account/reset-password/finish")
    suspend fun finishPasswordReset(@Body resetPasswordRequest: ResetPasswordRequest)
}