package com.budianto.mytourismapp.core.data.source.server.response

import com.google.gson.annotations.SerializedName

data class ListTourismResponse (

        @field:SerializedName("error")
        val error: String,

        @field:SerializedName("message")
        val message: String,

        @field:SerializedName("places")
        val places: List<TourismResponse>
)