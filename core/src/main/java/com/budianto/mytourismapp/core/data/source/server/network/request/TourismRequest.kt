package com.budianto.mytourismapp.core.data.source.server.network.request

import com.google.gson.annotations.SerializedName

data class TourismRequest(

        @field:SerializedName("id")
        var id: String,

        @field:SerializedName("name")
        var name: String,

        @field:SerializedName("description")
        var description: String,

        @field:SerializedName("address")
        var address: String,

        @field:SerializedName("latitude")
        var latitude: Double,

        @field:SerializedName("longitude")
        var longitude: Double,

        @field:SerializedName("like")
        var like: Int,

        @field:SerializedName("image")
        var image: String
)