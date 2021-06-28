package com.budianto.mytourismapp.core.data.source.server.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

    @field:SerializedName("error_message")
    val error_message: String? = null,

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("imagesUrl")
    val imagesUrl: String? = null

)
