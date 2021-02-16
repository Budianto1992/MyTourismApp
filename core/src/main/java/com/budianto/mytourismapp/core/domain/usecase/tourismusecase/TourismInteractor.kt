package com.budianto.mytourismapp.core.domain.usecase.tourismusecase

import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.core.domain.repository.ITourismRepository
import kotlinx.coroutines.flow.Flow

class TourismInteractor(private val tourismRepository: ITourismRepository) : TourismUseCase {
    override fun getAllTourism(): Flow<Resource<List<Tourism>>> = tourismRepository.getAllTourism()

    override fun getFavoriteTourism(): Flow<List<Tourism>> = tourismRepository.getFavoriteTourism()

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        tourism.isFavorite = state
        tourismRepository.setFavoriteTourism(tourism, state)
    }
}