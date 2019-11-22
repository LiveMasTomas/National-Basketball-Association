package com.tomas.nationalbasketballassociation.adapter

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.tomas.nationalbasketballassociation.R
import com.tomas.nationalbasketballassociation.model.Player

class PlayerViewHolder(player: View) : RecyclerView.ViewHolder(player) {

    //Grabbing each view individually for ease
    private val rootView: ConstraintLayout = player.findViewById(R.id.playerConstraintLayout)
    private val playerName: TextView = player.findViewById(R.id.playerNameText)
    private val playerPosition: TextView = player.findViewById(R.id.playerPositionText)
    private val playerPositionTitle: TextView = player.findViewById(R.id.playerPosition)
    private val playerHeight: TextView = player.findViewById(R.id.playerHeightText)
    private val playerHeightTitle: TextView = player.findViewById(R.id.playerHeight)
    private val playerWeight: TextView = player.findViewById(R.id.playerWeightText)
    private val playerWeightTitle: TextView = player.findViewById(R.id.playerWeight)
    private val playerTeam: TextView = player.findViewById(R.id.playerTeamText)
    private val playerConference: TextView = player.findViewById(R.id.playerConferenceText)

    fun bindPlayer(playerInfo: Player) {
        changeConstraints(false)
        changeTextViewBackgroundColor(android.R.color.white)
        playerName.text = playerInfo.name
        if (playerInfo.position.isBlank()) {
            playerPosition.visibility = View.GONE
            playerPositionTitle.visibility = View.GONE
        } else {
            playerPosition.visibility = View.VISIBLE
            playerPositionTitle.visibility = View.VISIBLE
            playerPosition.text = playerInfo.position
        }
        playerInfo.height?.let {
            playerHeight.visibility = View.VISIBLE
            playerHeightTitle.visibility = View.VISIBLE
            playerHeight.text = it
        } ?: run {
            playerHeight.visibility = View.GONE
            playerHeightTitle.visibility = View.GONE
        }
        playerInfo.weight?.let {
            playerWeight.visibility = View.VISIBLE
            playerWeightTitle.visibility = View.VISIBLE
            playerWeight.text = it
        } ?: run {
            playerWeight.visibility = View.GONE
            playerWeightTitle.visibility = View.GONE
        }
        playerTeam.text = playerInfo.team
        playerConference.text = playerInfo.conference
    }

    fun bindPlaceHolder() {
        changeConstraints(true)
        changeTextViewBackgroundColor(R.color.colorPrimaryDark)
    }

    //This will probably hit performance but i want it to look nice
    private fun changeConstraints(empty: Boolean) {
        val constraintSet = ConstraintSet().apply {
            clone(rootView)
        }
        if (empty) {
            with(constraintSet) {
                connect(R.id.playerNameText, ConstraintSet.END, R.id.cardGuide, ConstraintSet.START)
                connect(R.id.playerPositionText, ConstraintSet.END, R.id.cardGuide, ConstraintSet.START)
                connect(R.id.playerHeightText, ConstraintSet.END, R.id.cardGuide, ConstraintSet.START)
                connect(R.id.playerWeightText, ConstraintSet.END, R.id.cardGuide, ConstraintSet.START)
                connect(R.id.playerTeamText, ConstraintSet.END, R.id.cardGuide, ConstraintSet.START)
                connect(R.id.playerConferenceText, ConstraintSet.END, R.id.cardGuide, ConstraintSet.START)
            }
        } else {
            with(constraintSet) {
                connect(R.id.playerNameText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(R.id.playerPositionText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(R.id.playerHeightText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(R.id.playerWeightText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(R.id.playerTeamText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(R.id.playerConferenceText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            }
        }
        constraintSet.applyTo(rootView)
    }

    private fun changeTextViewBackgroundColor(@ColorRes color: Int) {
        playerName.setBackgroundColor(playerName.context.getColor(color))
        playerPosition.setBackgroundColor(playerPosition.context.getColor(color))
        playerHeight.setBackgroundColor(playerHeight.context.getColor(color))
        playerWeight.setBackgroundColor(playerWeight.context.getColor(color))
        playerTeam.setBackgroundColor(playerTeam.context.getColor(color))
        playerConference.setBackgroundColor(playerConference.context.getColor(color))
    }
}