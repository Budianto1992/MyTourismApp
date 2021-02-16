package com.budianto.mytourismapp.core.data.source.server.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkRequestInterceptor(private val networkRequestHeader: List<NetworkRequestHeader>): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
                networkRequestHeader.forEach {
                    requestBuilder.addHeader(it.type, it.value)
                }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}