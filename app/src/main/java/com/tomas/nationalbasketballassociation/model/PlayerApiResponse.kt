package com.tomas.nationalbasketballassociation.model

import com.squareup.moshi.Json

data class PlayerApiResponse(
        @Json(name = "data")
        val `data`: List<Data>,
        @Json(name = "meta")
        val meta: Meta
)