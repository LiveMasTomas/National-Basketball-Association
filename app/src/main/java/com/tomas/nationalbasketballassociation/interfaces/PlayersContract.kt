package com.tomas.nationalbasketballassociation.interfaces

import com.tomas.nationalbasketballassociation.network.PlayerViewState

interface PlayersContract {
    /**
     * Sending the state of the response to the view via interface
     */
    fun setViewState(state: PlayerViewState)
}