package com.tomas.nationalbasketballassociation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tomas.nationalbasketballassociation.interfaces.SearchContract
import com.tomas.nationalbasketballassociation.model.Player
import com.tomas.nationalbasketballassociation.network.PlayerViewState
import com.tomas.nationalbasketballassociation.paging.SearchPlayersDataSource
import com.tomas.nationalbasketballassociation.paging.SearchPlayersDataSourceFactory
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchPlayerViewModel(application: Application) : AndroidViewModel(application), SearchContract {

    private var pagedList: LiveData<PagedList<Player>>? = null
    private val _searchApiResult = MutableLiveData<PlayerViewState>()
    private val factory: SearchPlayersDataSourceFactory by application.inject { parametersOf(this) }

    private val dataSource: SearchPlayersDataSource?
        get() = pagedList?.value?.dataSource as? SearchPlayersDataSource

    var searchListener: (() -> String)? = null

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .build()

        pagedList = LivePagedListBuilder(factory, config).build()
    }

    fun invalidateData() = dataSource?.invalidate()

    val searchPlayerResults = MediatorLiveData<PlayerViewState>().apply {
        addSource(_searchApiResult) {
            value = it
        }
        pagedList?.let { liveData ->
            addSource(liveData) {
                value = PlayerViewState.Players(it)
            }
        }
    }

    override fun getSearchTerm(): String = searchListener?.invoke().orEmpty()

    override fun setViewState(state: PlayerViewState) {
        _searchApiResult.postValue(state)
    }

    companion object {
        val TAG: String by lazy { SearchPlayerViewModel::class.java.simpleName }
        private const val PAGE_SIZE = 50
    }
}