package com.budianto.mytourismapp.core.data.source.server.network.request

import com.budianto.mytourismapp.core.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserRequest(
        @SerializedName("login")
        val username: String?,

        @SerializedName("email")
        val email: String?,

        @SerializedName("firstName")
        val firstName: String?,

        @SerializedName("lastName")
        val lastName: String?
)

fun User.toRequest(): UserRequest = UserRequest(
        username, email, firstName, lastName
)
