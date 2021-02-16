package com.budianto.mytourismapp.core.data.source.server.network.request

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @field:SerializedName("key")
    val key: String,

    @field:SerializedName("newPassword")
    val newPassword: String
)