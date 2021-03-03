package com.budianto.mytourismapp.core.data.source.server.network.request

import com.budianto.mytourismapp.core.domain.model.User
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("login")
    val username: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("password")
    val password: String?
)

fun User.toRegisterRequest(): RegisterRequest = RegisterRequest(username, email, password)