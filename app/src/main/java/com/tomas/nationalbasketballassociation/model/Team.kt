package com.tomas.nationalbasketballassociation.model

import com.squareup.moshi.Json

data class Team(
        @Json(name = "abbreviation")
        val abbreviation: String, // LAL
        @Json(name = "city")
        val city: String, // Los Angeles
        @Json(name = "conference")
        val conference: String, // West
        @Json(name = "division")
        val division: String, // Pacific
        @Json(name = "full_name")
        val fullName: String, // Los Angeles Lakers
        @Json(name = "id")
        val id: Int, // 14
        @Json(name = "name")
        val name: String // Lakers
)