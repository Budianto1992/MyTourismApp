package com.budianto.mytourismapp.core.domain.usecase.user.logout

import com.budianto.mytourismapp.core.data.shared.service.SessionManager

class LogoutUserImpl(private val sessionManager: SessionManager): LogoutUserUseCase {
    override fun execute() = sessionManager.removeAuthToken()
}