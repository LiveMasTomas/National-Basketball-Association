package com.tomas.nationalbasketballassociation.paging

import android.app.Application
import androidx.paging.DataSource
import com.tomas.nationalbasketballassociation.interfaces.PlayersContract
import com.tomas.nationalbasketballassociation.model.Player

class AllPlayersDataSourceFactory(
        private val application: Application,
        private val playersContract: PlayersContract) : DataSource.Factory<Int, Player>() {
    override fun create(): DataSource<Int, Player> = AllPlayerDataSource(application, playersContract)
}