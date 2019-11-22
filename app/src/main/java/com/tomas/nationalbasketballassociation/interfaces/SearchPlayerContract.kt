package com.tomas.nationalbasketballassociation.interfaces

interface SearchPlayerContract : PlayersContract {
    /**
     * get name to search players
     */
    fun getSearchTerm(): String
}