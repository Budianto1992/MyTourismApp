package com.budianto.mytourismapp.core.util

import com.budianto.mytourismapp.core.data.source.local.entity.TourismEntity
import com.budianto.mytourismapp.core.data.source.server.network.request.TourismRequest
import com.budianto.mytourismapp.core.domain.model.Tourism

object DataMapping {

    fun mapResponseToEntites(input: List<TourismRequest>): List<TourismEntity>{
        val tourismList = ArrayList<TourismEntity>()
        input.map {
            val tourism = TourismEntity(
                    tourismId = it.id,
                    name = it.name,
                    description = it.description,
                    address = it.address,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    like = it.like,
                    image = it.image,
                    isFavorite = false
            )

            tourismList.add(tourism)
        }

        return tourismList
    }

    fun mapEntitiesToDomain(input: List<TourismEntity>): List<Tourism> =
            input.map {
                Tourism(
                        tourismId = it.tourismId,
                        name = it.name,
                        description = it.description,
                        address = it.address,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        like = it.like,
                        image = it.image,
                        isFavorite = it.isFavorite
                )
            }

    fun mapDomainToEntites(input: Tourism) = TourismEntity(
            tourismId = input.tourismId,
            name = input.name,
            description = input.description,
            address = input.address,
            latitude = input.latitude,
            longitude = input.longitude,
            like = input.like,
            image = input.image,
            isFavorite = input.isFavorite
    )
}