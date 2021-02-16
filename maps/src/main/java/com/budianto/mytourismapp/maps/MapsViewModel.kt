package com.budianto.mytourismapp.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.budianto.mytourismapp.core.domain.usecase.tourismusecase.TourismUseCase

class MapsViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    val tourism = tourismUseCase.getAllTourism().asLiveData()
}