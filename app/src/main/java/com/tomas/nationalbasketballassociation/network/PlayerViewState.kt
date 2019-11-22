package com.tomas.nationalbasketballassociation.network

import androidx.paging.PagedList
import com.tomas.nationalbasketballassociation.model.Player

sealed class PlayerViewState {

    /**
     * waiting for api response
     */
    object Loading : PlayerViewState()

    /**
     * got api response
     */
    object Loaded : PlayerViewState()

    /**
     * holding the actual players
     */
    data class Players(val players: PagedList<Player>) : PlayerViewState()

    /**
     * holds regular error, includes page to know if it happens on load or while scrolling
     */
    data class Error(val t: Throwable, val page: Int) : PlayerViewState()

    /**
     * no players were returned from API
     */
    object Empty : PlayerViewState()

}