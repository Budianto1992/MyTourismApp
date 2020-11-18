package com.budianto.mytourismapp

import android.app.Application
import com.budianto.mytourismapp.core.di.databaseModule
import com.budianto.mytourismapp.core.di.networkModule
import com.budianto.mytourismapp.core.di.repositoryModule
import com.budianto.mytourismapp.di.useCaseModue
import com.budianto.mytourismapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModue,
                    viewModelModule
                )
            )
        }
    }
}