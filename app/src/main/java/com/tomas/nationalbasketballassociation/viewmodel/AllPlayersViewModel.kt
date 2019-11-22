package com.tomas.nationalbasketballassociation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tomas.nationalbasketballassociation.interfaces.PlayersContract
import com.tomas.nationalbasketballassociation.model.Player
import com.tomas.nationalbasketballassociation.network.PlayerViewState
import com.tomas.nationalbasketballassociation.paging.AllPlayerDataSource
import com.tomas.nationalbasketballassociation.paging.AllPlayersDataSourceFactory
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AllPlayersViewModel(application: Application) : AndroidViewModel(application), PlayersContract {

    private var pagedList: LiveData<PagedList<Player>>? = null
    private val _playerApiResult = MutableLiveData<PlayerViewState>()
    private val dataSourceFactory: AllPlayersDataSourceFactory by application.inject { parametersOf(this) }

    private val playerDataSource: AllPlayerDataSource?
        get() = pagedList?.value?.dataSource as? AllPlayerDataSource

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .build()

        pagedList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun invalidateData() = playerDataSource?.invalidate()

    val nbaPlayerResults = MediatorLiveData<PlayerViewState>().apply {
        addSource(_playerApiResult) {
            value = it
        }
        pagedList?.let { liveData ->
            addSource(liveData) {
                value = PlayerViewState.Players(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerDataSource?.clearCoroutineJobs()
    }

    override fun setViewState(state: PlayerViewState) {
        _playerApiResult.postValue(state)
    }

    companion object {
        val TAG: String by lazy { AllPlayersViewModel::class.java.simpleName }
        private const val PAGE_SIZE = 50
    }
}