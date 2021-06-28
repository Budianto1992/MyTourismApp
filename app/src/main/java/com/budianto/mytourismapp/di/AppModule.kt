package com.budianto.mytourismapp.di

import com.budianto.mytourismapp.account.AccountViewModel
import com.budianto.mytourismapp.core.domain.usecase.TourismInteractor
import com.budianto.mytourismapp.core.domain.usecase.TourismUseCase
import com.budianto.mytourismapp.core.domain.usecase.account.AccountInteractor
import com.budianto.mytourismapp.core.domain.usecase.account.AccountUseCase
import com.budianto.mytourismapp.detail.DetailTourismViewModel
import com.budianto.mytourismapp.favorite.FavoriteViewModel
import com.budianto.mytourismapp.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModue = module {
    factory<TourismUseCase> { TourismInteractor(get()) }
    factory<AccountUseCase> { AccountInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }
    viewModel { AccountViewModel(get()) }
}