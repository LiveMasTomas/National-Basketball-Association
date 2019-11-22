package com.tomas.nationalbasketballassociation.network

import com.tomas.nationalbasketballassociation.model.PlayerNetworkModel
import com.tomas.nationalbasketballassociation.utils.toPlayerNetworkModel

class NbaPlayerWebService(private val playerPlayerApi: NbaPlayerApi) {

    suspend fun getAllPlayers(page: Int = 0, pageSize: Int = 25): PlayerNetworkModel =
            playerPlayerApi.getAllPlayers(page, pageSize).toPlayerNetworkModel()

    suspend fun searchForPlayer(playerName: String, page: Int = 0, pageSize: Int = 25): PlayerNetworkModel =
            playerPlayerApi.searchPlayer(playerName, page, pageSize).toPlayerNetworkModel()
}