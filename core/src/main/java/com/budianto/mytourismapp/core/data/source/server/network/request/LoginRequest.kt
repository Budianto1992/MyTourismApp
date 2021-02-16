package com.budianto.mytourismapp.core.data.source.server.network.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @field:SerializedName("username")
        var username: String,

        @field:SerializedName("password")
        var password: String,

        @field:SerializedName("rememberMe")
        var isRememberMe: Boolean
)