package com.tomas.nationalbasketballassociation.paging

import android.app.Application
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.tomas.nationalbasketballassociation.interfaces.SearchContract
import com.tomas.nationalbasketballassociation.model.Player
import com.tomas.nationalbasketballassociation.network.NbaPlayerWebService
import com.tomas.nationalbasketballassociation.network.PlayerViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchPlayersDataSource(application: Application,
                              private val contract: SearchContract) : PageKeyedDataSource<Int, Player>() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private val webService: NbaPlayerWebService by application.inject()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Player>) {
        contract.setViewState(PlayerViewState.Loading)
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val response = webService.searchForPlayer(contract.getSearchTerm(), 0, params.requestedLoadSize)
                callback.onResult(
                        response.players,
                        0,
                        response.paging.totalCount,
                        null,
                        response.paging.nextPage
                )
                if (response.players.isEmpty()) {
                    contract.setViewState(PlayerViewState.Empty)
                } else {
                    contract.setViewState(PlayerViewState.Loaded)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to Load", e)
                contract.setViewState(PlayerViewState.Error(e, 0))
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Player>) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val response = webService.searchForPlayer(contract.getSearchTerm(), params.key, params.requestedLoadSize)
                callback.onResult(response.players, response.paging.nextPage)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load next page: ${params.key}", e)
                contract.setViewState(PlayerViewState.Error(e, params.key))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Player>) = Unit

    fun clearCoroutineJobs() = completableJob.cancel()

    companion object {
        val TAG: String by lazy { SearchPlayersDataSource::class.java.simpleName }
    }
}