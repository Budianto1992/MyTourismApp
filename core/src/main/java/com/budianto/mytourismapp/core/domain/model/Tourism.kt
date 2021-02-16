package com.budianto.mytourismapp.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tourism(

        var tourismId: String,
        var name: String,
        var description: String,
        var address: String,
        var latitude: Double,
        var longitude: Double,
        var like: Int,
        var image: String,
        var isFavorite: Boolean = false
): Parcelable