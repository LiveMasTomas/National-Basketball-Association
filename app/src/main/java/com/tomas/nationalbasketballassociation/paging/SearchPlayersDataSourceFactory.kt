package com.tomas.nationalbasketballassociation.paging

import android.app.Application
import androidx.paging.DataSource
import com.tomas.nationalbasketballassociation.interfaces.SearchPlayerContract
import com.tomas.nationalbasketballassociation.model.Player

class SearchPlayersDataSourceFactory(private val application: Application,
                                     private val contract: SearchPlayerContract) : DataSource.Factory<Int, Player>() {
    override fun create(): DataSource<Int, Player> =
            SearchPlayersDataSource(application, contract)
}