package com.budianto.mytourismapp.core.data.shared.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


private val modules = module {
    factory {
        androidApplication().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }
}

val sharedModules = listOf(modules)