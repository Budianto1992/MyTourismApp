package com.budianto.mytourismapp.core.data.source.server.network.request

import com.budianto.mytourismapp.core.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserIdRequest(
    @SerializedName("id")
    val id: Long?
)

fun User.toUserIdRequest(): UserIdRequest = UserIdRequest(id)