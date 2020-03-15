package com.tomas.nationalbasketballassociation.koin

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomas.nationalbasketballassociation.BuildConfig
import com.tomas.nationalbasketballassociation.interfaces.PlayersContract
import com.tomas.nationalbasketballassociation.interfaces.SearchContract
import com.tomas.nationalbasketballassociation.network.NbaPlayerApi
import com.tomas.nationalbasketballassociation.network.NbaPlayerWebService
import com.tomas.nationalbasketballassociation.paging.AllPlayersDataSourceFactory
import com.tomas.nationalbasketballassociation.paging.SearchPlayersDataSourceFactory
import com.tomas.nationalbasketballassociation.viewmodel.AllPlayersViewModel
import com.tomas.nationalbasketballassociation.viewmodel.SearchPlayerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val webServiceKoinModule = module {
    factory { NbaPlayerWebService(get()) }
}

val dataSourceFactoryKoinModule = module {
    factory { (playerContract: PlayersContract) -> AllPlayersDataSourceFactory(get(), playerContract) }
    factory { (contract: SearchContract) -> SearchPlayersDataSourceFactory(get(), contract) }
}

val viewModelKoinModule = module {
    viewModel { AllPlayersViewModel(get()) }
    viewModel { SearchPlayerViewModel(get()) }
}

val retroFitModule = module {
    single {
        Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(NbaPlayerApi::class.java)
    }
}

private val moshi: Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()