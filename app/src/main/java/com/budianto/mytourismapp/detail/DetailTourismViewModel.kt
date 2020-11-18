package com.budianto.mytourismapp.detail

import androidx.lifecycle.ViewModel
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.core.domain.usecase.TourismUseCase

class DetailTourismViewModel(private val tourismUseCase: TourismUseCase) : ViewModel() {

    fun setFavoriteTourism(tourism: Tourism, newState: Boolean) = tourismUseCase.setFavoriteTourism(tourism, newState)
}