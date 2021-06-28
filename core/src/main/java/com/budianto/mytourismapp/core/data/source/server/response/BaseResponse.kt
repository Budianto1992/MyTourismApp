package com.budianto.mytourismapp.core.data.source.server.response

import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(

    @field:SerializedName("status_code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("error_message")
    val error_message: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)
