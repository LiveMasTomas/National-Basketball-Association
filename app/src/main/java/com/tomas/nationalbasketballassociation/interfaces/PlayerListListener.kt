package com.tomas.nationalbasketballassociation.interfaces

interface PlayerListListener {

    /**
     * user pulls to refresh
     */
    fun onRefreshRequested()

    /**
     * user clicks on retry button
     */
    fun onRetryRequested()
}