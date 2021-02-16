package com.budianto.mytourismapp.core.data.source.server.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @field:SerializedName("status_code")
        var statusCode: String,

        @field:SerializedName("id_token")
        var authToken: String,
)