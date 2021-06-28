package com.budianto.mytourismapp.core.di

import androidx.room.Room
import com.budianto.mytourismapp.core.data.repository.AccountRepository
import com.budianto.mytourismapp.core.data.repository.TourismRepository
import com.budianto.mytourismapp.core.data.source.local.LocalDataSource
import com.budianto.mytourismapp.core.data.source.local.room.DatabaseTourism
import com.budianto.mytourismapp.core.data.source.server.RemoteDataSource
import com.budianto.mytourismapp.core.data.source.server.network.ApiService
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import com.budianto.mytourismapp.core.domain.repository.ITourismRepository
import com.budianto.mytourismapp.core.util.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL_LOCAL_SERVICES = "http://192.168.43.99:8080"
const val BASE_URL_WEB_SERVICES = "https://tourism-api.dicoding.dev/"


val databaseModule = module {
    factory { get<DatabaseTourism>().tourismDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            DatabaseTourism::class.java, "tourism.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }


    single {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_LOCAL_SERVICES)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get())}
    single { RemoteDataSource(get())}
    factory { AppExecutors() }

    single<ITourismRepository> { TourismRepository(get(), get(), get()) }
    single<IAccountRepository> { AccountRepository(get()) }
}