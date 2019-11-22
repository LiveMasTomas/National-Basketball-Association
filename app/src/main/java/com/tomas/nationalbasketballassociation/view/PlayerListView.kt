package com.tomas.nationalbasketballassociation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tomas.nationalbasketballassociation.R
import com.tomas.nationalbasketballassociation.adapter.PlayerPagedListAdapter
import com.tomas.nationalbasketballassociation.interfaces.PlayerListListener
import com.tomas.nationalbasketballassociation.model.Data
import com.tomas.nationalbasketballassociation.model.Player
import com.tomas.nationalbasketballassociation.view.PlayerListView.State.*
import kotlinx.android.synthetic.main.view_player_list.view.*
import kotlinx.android.synthetic.main.view_player_list_empty_error.view.*

class PlayerListView @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    private var listener: PlayerListListener? = null
    private var adapter: PlayerPagedListAdapter? = null

    var state: State = LOADING
        set(value) {
            if (field != value) {
                field = value
                updateView(field)
            }
        }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_player_list, this)
        inflater.inflate(R.layout.view_player_list_empty_error, this)

        playerListSwipeLayout.setOnRefreshListener {
            state = LOADING
            listener?.onRefreshRequested()
        }

        stateViewRetryButton.setOnClickListener {
            state = LOADING
            listener?.onRetryRequested()
        }

        //init state should be loading
        playerListStateView.visibility = View.INVISIBLE
        playerListRV.visibility = View.INVISIBLE
        playerListSwipeLayout.isRefreshing = true
        playerListSwipeLayout.isEnabled = false
        playerListProgressBar.visibility = View.VISIBLE
    }

    fun setData(players: PagedList<Player>) {
        adapter?.submitList(players)
    }

    /**
     * here we attach the adapter and listener to this view (which contains the recycler view to show
     * all the players
     */
    fun setPlayerListAdapter(adapter: PlayerPagedListAdapter, listener: PlayerListListener) {
        this.adapter = adapter
        this.listener = listener
        with(playerListRV) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }
    }

    private fun updateView(state: State) {
        when (state) {
            LOADING -> {
                playerListStateView.visibility = View.INVISIBLE
                playerListRV.visibility = View.INVISIBLE
                playerListSwipeLayout.isRefreshing = true
                playerListSwipeLayout.isEnabled = false
                playerListProgressBar.visibility = View.VISIBLE
            }
            LOAD_EMPTY -> {
                playerListStateView.visibility = View.VISIBLE
                playerListRV.visibility = View.INVISIBLE
                stateViewRetryButton.visibility = View.GONE
                playerListSwipeLayout.isRefreshing = false
                playerListSwipeLayout.isEnabled = false
                changeViewStateText(true)
                playerListProgressBar.visibility = View.GONE
            }
            LOAD_WITH_DATA -> {
                playerListStateView.visibility = View.INVISIBLE
                playerListRV.visibility = View.VISIBLE
                playerListSwipeLayout.isEnabled = true
                playerListSwipeLayout.isRefreshing = false
                playerListProgressBar.visibility = View.GONE
            }
            ERROR -> {
                playerListStateView.visibility = View.VISIBLE
                stateViewRetryButton.visibility = View.VISIBLE
                playerListRV.visibility = View.INVISIBLE
                playerListSwipeLayout.isRefreshing = false
                playerListSwipeLayout.isEnabled = true
                changeViewStateText(false)
                playerListProgressBar.visibility = View.GONE
            }
        }
    }

    private fun changeViewStateText(empty: Boolean) {
        stateHeaderTextView.text = context.getText(if (empty) {
            R.string.list_view_empty_header
        } else {
            R.string.list_view_error_header
        })
        stateSubtitleTextView.text = context.getText(if (empty) {
            R.string.list_view_empty_subtitle
        } else {
            R.string.list_view_error_subtitle
        })
    }

    enum class State {
        LOADING,
        LOAD_EMPTY,
        LOAD_WITH_DATA,
        ERROR
    }
}