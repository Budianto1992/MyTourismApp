package com.budianto.mytourismapp.core.data.source.server.network.response

import com.budianto.mytourismapp.core.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("id")
        val id: Long?,

        @SerializedName("login")
        val username: String?,

        @SerializedName("email")
        val email: String?,

        @SerializedName("firstName")
        val firstName: String?,

        @SerializedName("lastName")
        val lastName: String?
)

fun UserResponse.toDomain(): User = User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName
)

fun User.toResponse(): UserResponse = UserResponse(id, username, email, firstName, lastName)