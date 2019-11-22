package com.tomas.nationalbasketballassociation.utils

import com.tomas.nationalbasketballassociation.model.PagingMeta
import com.tomas.nationalbasketballassociation.model.Player
import com.tomas.nationalbasketballassociation.model.PlayerApiResponse
import com.tomas.nationalbasketballassociation.model.PlayerNetworkModel

fun PlayerApiResponse.toPlayerNetworkModel(): PlayerNetworkModel {
    val page = PagingMeta(nextPage = meta.nextPage, totalCount = meta.totalCount)
    val players = data.map { info ->
        Player(
                id = info.id,
                name = "${info.firstName} ${info.lastName}",
                position = info.position,
                height = if (info.heightInches == null || info.heightFeet == null) {
                    null
                } else {
                    "${info.heightFeet}' ${info.heightInches}\""
                },
                weight = info.weightPounds?.let { "$it lbs" },
                team = info.team.fullName,
                conference = info.team.conference
        )
    }
    return PlayerNetworkModel(players, page)
}