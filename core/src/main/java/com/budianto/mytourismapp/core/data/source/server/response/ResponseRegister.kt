package com.budianto.mytourismapp.core.data.source.server.response

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("longitude")
    val longitude: Int? = null,

    @field:SerializedName("latitude")
    val latitude: Int? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("imagesProfile")
    val imagesProfile: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,
)