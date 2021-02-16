package com.budianto.mytourismapp.core.domain.usecase.tourismusecase

import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface TourismUseCase {

    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}