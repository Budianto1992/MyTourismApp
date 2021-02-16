package com.budianto.mytourismapp.core.domain.repository

import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.data.source.server.network.request.LoginRequest
import com.budianto.mytourismapp.core.data.source.server.network.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun login(request: LoginRequest): Resource<LoginResponse>
}