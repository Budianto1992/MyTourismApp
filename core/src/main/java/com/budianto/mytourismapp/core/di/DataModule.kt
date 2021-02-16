package com.budianto.mytourismapp.core.di

import androidx.room.Room
import com.budianto.mytourismapp.core.data.repository.AccountRepository
import com.budianto.mytourismapp.core.data.repository.LoginRepository
import com.budianto.mytourismapp.core.data.repository.TourismRepository
import com.budianto.mytourismapp.core.data.source.local.LocalDataSource
import com.budianto.mytourismapp.core.data.source.local.room.DatabaseTourism
import com.budianto.mytourismapp.core.data.source.server.AccountDataSource
import com.budianto.mytourismapp.core.data.source.server.DataSourceProvider
import com.budianto.mytourismapp.core.data.source.server.LoginDataSource
import com.budianto.mytourismapp.core.data.source.server.TourismRemoteDataSource
import com.budianto.mytourismapp.core.data.source.server.network.NetworkServiceFactory
import com.budianto.mytourismapp.core.data.source.server.network.ServiceFactory
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.AccountApi
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.LoginApi
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.TourismApi
import com.budianto.mytourismapp.core.domain.repository.IAccountRepository
import com.budianto.mytourismapp.core.domain.repository.ILoginRepository
import com.budianto.mytourismapp.core.domain.repository.ITourismRepository
import com.budianto.mytourismapp.core.util.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    factory { HttpLoggingInterceptor.Level.BODY }

    single {
        val serviceFactory: ServiceFactory = NetworkServiceFactory(
                get(),
                get()
        )
        serviceFactory
    }

    single { get<Retrofit>().create(LoginApi::class.java) }
    single { get<Retrofit>().create(AccountApi::class.java) }
    single { get<Retrofit>().create(TourismApi::class.java) }
}

val databaseModule = module {
    factory { get<DatabaseTourism>().tourismDao() }
    single {
        val passpharse: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passpharse)
        Room.databaseBuilder(
                androidContext(),
                DatabaseTourism::class.java, "tourism.db"
        ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
    }
}

val repositoryModule = module {
    factory {
        val dataSourceProvider = DataSourceProvider(get())
        dataSourceProvider
    }

    single { LoginDataSource(get()) }
    single { AccountDataSource(get()) }
    single { LocalDataSource(get()) }
    single { TourismRemoteDataSource(get()) }

    single <IAccountRepository>{ AccountRepository(get()) }
    single<ILoginRepository> { LoginRepository(get(), get()) }
    factory { AppExecutors() }
    single<ITourismRepository> { TourismRepository(get(), get(), get()) }
}

val dataModules = networkModule + databaseModule + repositoryModule