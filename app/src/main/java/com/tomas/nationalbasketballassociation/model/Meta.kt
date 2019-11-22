package com.tomas.nationalbasketballassociation.model

import com.squareup.moshi.Json

data class Meta(
        @Json(name = "current_page")
        val currentPage: Int, // 1
        @Json(name = "next_page")
        val nextPage: Int, // 2
        @Json(name = "per_page")
        val perPage: Int, // 25
        @Json(name = "total_count")
        val totalCount: Int, // 9999
        @Json(name = "total_pages")
        val totalPages: Int // 50
)