package com.budianto.mytourismapp.core.data.source.server.network

import com.budianto.mytourismapp.core.data.shared.service.SessionManager
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class ServiceFactory(private val logLevel: HttpLoggingInterceptor.Level,
                              private val sessionManager: SessionManager) {


    fun <T> create(serviceType: Class<T>): T{
        return create(
                serviceType,
                getHttpClient()
        )
    }

    private fun <T> create(
            serviceType: Class<T>,
            client: OkHttpClient
    ): T {
        val retrofit = getNetAdapter(client)
        return retrofit.create(serviceType)
    }

    private fun getNetAdapter(client: OkHttpClient): Retrofit{
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://tourism-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
        return retrofit.build()
    }

    private fun getHttpClient(): OkHttpClient {
        val hostname = "tourism-api.dicoding.dev"
        val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/8gc0uDQf4jx7Yz04nlDIVpxhg+Z3I11j5zDSEaR3fvQ=")
                .add(hostname, "sha256/qPerI4uMwY1VrtRE5aBY8jIQJopLUuBt2+GDUWMwZn4=")
                .add(hostname, "sha256/iie1VXtL7HzAMF+/PVPR9xzT80kQxdZeJ+zduCB3uj0=")
                .build()
        val builder = OkHttpClient.Builder()
        interceptors(sessionManager).forEach { interceptor -> builder.addInterceptor(interceptor) }
        builder.addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
        return builder.build()
    }

    abstract fun interceptors(sessionManager: SessionManager): List<Interceptor>
}