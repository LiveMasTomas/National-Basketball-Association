package com.tomas.nationalbasketballassociation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tomas.nationalbasketballassociation.interfaces.SearchPlayerContract
import com.tomas.nationalbasketballassociation.model.Player
import com.tomas.nationalbasketballassociation.network.PlayerViewState
import com.tomas.nationalbasketballassociation.paging.SearchPlayersDataSource
import com.tomas.nationalbasketballassociation.paging.SearchPlayersDataSourceFactory
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchPlayerViewModel(application: Application) : AndroidViewModel(application), SearchPlayerContract {

    private var pagedList: LiveData<PagedList<Player>>? = null
    private val _searchPlayerResult = MutableLiveData<PlayerViewState>()
    private val dataSourceFactory: SearchPlayersDataSourceFactory by application.inject { parametersOf(this) }
    private val editTextObserver = Observer<String> { observedText = it }
    private var observedText: String? = null
    val searchEditText = MutableLiveData<String>()

    private val searchDataSource: SearchPlayersDataSource?
        get() = pagedList?.value?.dataSource as? SearchPlayersDataSource

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .build()

        pagedList = LivePagedListBuilder(dataSourceFactory, config).build()

        searchEditText.observeForever(editTextObserver)
    }

    fun invalidateData() = searchDataSource?.invalidate()

    val searchPlayerResult = MediatorLiveData<PlayerViewState>().apply {
        addSource(_searchPlayerResult) {
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
        searchEditText.removeObserver(editTextObserver)
        searchDataSource?.clearCoroutineJobs()
    }

    override fun getSearchTerm(): String = observedText.orEmpty()

    override fun setViewState(state: PlayerViewState) {
        _searchPlayerResult.postValue(state)
    }

    companion object {
        val TAG: String by lazy { SearchPlayerViewModel::class.java.simpleName }
        private const val PAGE_SIZE = 50
    }
}