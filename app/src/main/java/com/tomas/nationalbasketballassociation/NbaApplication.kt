package com.tomas.nationalbasketballassociation

import android.app.Application
import com.tomas.nationalbasketballassociation.koin.dataSourceFactoryKoinModule
import com.tomas.nationalbasketballassociation.koin.retroFitModule
import com.tomas.nationalbasketballassociation.koin.viewModelKoinModule
import com.tomas.nationalbasketballassociation.koin.webServiceKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class NbaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val allModules = listOf(retroFitModule, webServiceKoinModule, dataSourceFactoryKoinModule, viewModelKoinModule)
        GlobalContext.getOrNull()?.let {
            loadKoinModules(allModules)
        } ?: startKoin {
            androidContext(applicationContext)
            modules(allModules)
        }
    }
}