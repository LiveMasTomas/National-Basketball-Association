package com.tomas.nationalbasketballassociation.interfaces

interface SearchContract: PlayersContract {

    fun getSearchTerm(): String
}