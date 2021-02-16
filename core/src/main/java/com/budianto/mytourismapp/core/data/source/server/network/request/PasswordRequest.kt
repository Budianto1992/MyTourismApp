package com.budianto.mytourismapp.core.data.source.server.network.request

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @field:SerializedName("currentPassword")
    val currentPassword: String,

    @field:SerializedName("newPassword")
    val newPassword: String)