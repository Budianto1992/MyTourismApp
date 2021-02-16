package com.budianto.mytourismapp.core.data.source.server

import com.budianto.mytourismapp.core.data.source.server.network.ServiceFactory
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.AccountApi
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.LoginApi
import com.budianto.mytourismapp.core.data.source.server.network.apiretrofit.TourismApi

class DataSourceProvider(private val serviceFactory: ServiceFactory) {

    fun getLoginDataSource(): LoginApi{
        return serviceFactory.create(LoginApi::class.java)
    }

    fun getAccountDataSource(): AccountApi{
        return serviceFactory.create(AccountApi::class.java)
    }

    fun getAllTourism(): TourismApi{
        return serviceFactory.create(TourismApi::class.java)
    }
}