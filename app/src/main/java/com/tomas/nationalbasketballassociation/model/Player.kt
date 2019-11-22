package com.tomas.nationalbasketballassociation.model

data class Player(
        val id: Int,
        val name: String,
        val position: String, //not null but can comeback empty
        val height: String?,
        val weight: String?,
        val team: String,
        val conference: String)

data class PagingMeta(
        val nextPage: Int,
        val totalCount: Int
)

data class PlayerNetworkModel(
        val players: List<Player>,
        val paging: PagingMeta
)
