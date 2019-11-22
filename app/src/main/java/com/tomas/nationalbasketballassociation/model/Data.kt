package com.tomas.nationalbasketballassociation.model

import com.squareup.moshi.Json

data class Data(
        @Json(name = "first_name")
        val firstName: String, // LeBron
        @Json(name = "height_feet")
        val heightFeet: Int?, // 6
        @Json(name = "height_inches")
        val heightInches: Int?, // 8
        @Json(name = "id")
        val id: Int, // 237
        @Json(name = "last_name")
        val lastName: String, // James
        @Json(name = "position")
        val position: String,
        @Json(name = "team")// F
        val team: Team,
        @Json(name = "weight_pounds")
        val weightPounds: Int? // 250
)