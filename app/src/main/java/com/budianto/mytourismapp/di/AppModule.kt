package com.budianto.mytourismapp.di

import com.budianto.mytourismapp.core.data.shared.service.SessionManager
import com.budianto.mytourismapp.core.domain.usecase.user.login.LoginInteractor
import com.budianto.mytourismapp.core.domain.usecase.user.login.LoginUseCase
import com.budianto.mytourismapp.core.domain.usecase.tourismusecase.TourismInteractor
import com.budianto.mytourismapp.core.domain.usecase.tourismusecase.TourismUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.activateuser.ActivateUserImpl
import com.budianto.mytourismapp.core.domain.usecase.user.activateuser.ActivateUserUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.getuser.GetUserImpl
import com.budianto.mytourismapp.core.domain.usecase.user.getuser.GetUserUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.logout.LogoutUserImpl
import com.budianto.mytourismapp.core.domain.usecase.user.logout.LogoutUserUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.password.*
import com.budianto.mytourismapp.core.domain.usecase.user.register.RegisterUserImpl
import com.budianto.mytourismapp.core.domain.usecase.user.register.RegisterUserUseCase
import com.budianto.mytourismapp.core.domain.usecase.user.updateuser.UpdateUserImpl
import com.budianto.mytourismapp.core.domain.usecase.user.updateuser.UpdateUserUseCase
import com.budianto.mytourismapp.detail.DetailTourismViewModel
import com.budianto.mytourismapp.favorite.FavoriteViewModel
import com.budianto.mytourismapp.home.HomeViewModel
import com.budianto.mytourismapp.user.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModue = module {
    factory <TourismUseCase> { TourismInteractor(get()) }
    factory <LoginUseCase>{ LoginInteractor(get(), get()) }
    factory <LogoutUserUseCase>{ LogoutUserImpl(get()) }
    factory <RegisterUserUseCase>{ RegisterUserImpl(get()) }
    factory <ActivateUserUseCase>{ ActivateUserImpl(get()) }
    factory <GetUserUseCase>{ GetUserImpl(get()) }
    factory <UpdateUserUseCase>{ UpdateUserImpl(get()) }
    factory <UpdatePasswordUseCase>{ UpdatePasswordImpl(get()) }
    factory <StartResetPasswordUseCase>{ StartResetPasswordImpl(get()) }
    factory <FinishResetPasswordUseCase>{ FinishResetPasswordImpl(get()) }

    single { SessionManager(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }

    viewModel { LoginViewModel(get(), get()) }
}