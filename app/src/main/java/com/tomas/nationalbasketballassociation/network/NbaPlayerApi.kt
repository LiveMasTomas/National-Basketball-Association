package com.tomas.nationalbasketballassociation.network

import com.tomas.nationalbasketballassociation.model.PlayerApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaPlayerApi {

    @GET("players")
    suspend fun getAllPlayers(
            @Query("page") page: Int = 0,
            @Query("per_page") perPage: Int = 25): PlayerApiResponse

    @GET("players")
    suspend fun searchPlayer(
            @Query("search") search: String,
            @Query("page") page: Int = 0,
            @Query("per_page") offset: Int = 25): PlayerApiResponse
}