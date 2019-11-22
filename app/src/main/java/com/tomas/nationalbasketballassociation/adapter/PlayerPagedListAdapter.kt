package com.tomas.nationalbasketballassociation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tomas.nationalbasketballassociation.R
import com.tomas.nationalbasketballassociation.model.Player

class PlayerPagedListAdapter : PagedListAdapter<Player, PlayerViewHolder>(PLAYER_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder =
            PlayerViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_player, parent, false))

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        //null check for whole player, if null then there should be a placeholder
        getItem(position)?.let {
            holder.bindPlayer(it)
        } ?: run {
            holder.bindPlaceHolder()
        }
    }

    companion object {
        val PLAYER_DIFF = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
                    oldItem == newItem
        }
    }
}