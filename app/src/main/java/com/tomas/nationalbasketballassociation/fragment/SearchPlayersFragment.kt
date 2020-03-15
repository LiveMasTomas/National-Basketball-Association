package com.tomas.nationalbasketballassociation.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tomas.nationalbasketballassociation.MainActivity
import com.tomas.nationalbasketballassociation.R
import com.tomas.nationalbasketballassociation.adapter.PlayerPagedListAdapter
import com.tomas.nationalbasketballassociation.interfaces.PlayerListListener
import com.tomas.nationalbasketballassociation.network.PlayerViewState
import com.tomas.nationalbasketballassociation.view.PlayerListView.State.*
import com.tomas.nationalbasketballassociation.viewmodel.SearchPlayerViewModel
import kotlinx.android.synthetic.main.fragment_search_players.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchPlayersFragment : Fragment() {

    private val searchPlayersViewModel: SearchPlayerViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_players, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setting up search in toolbar
        (activity as? MainActivity)?.setSupportActionBar(searchPlayersToolBar)
        (activity as? MainActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHasOptionsMenu(true)
        }

        val listener = object : PlayerListListener {
            override fun onRefreshRequested() {
                searchPlayersViewModel.invalidateData()
            }

            override fun onRetryRequested() {
                searchPlayersViewModel.invalidateData()
            }

        }

        searchPlayersListView.setPlayerListAdapter(PlayerPagedListAdapter(), listener)

        searchPlayersViewModel.searchPlayerResults.observe(viewLifecycleOwner, Observer {
            when (it) {
                is PlayerViewState.Loading -> {
                    searchPlayersListView.state = LOADING
                }
                is PlayerViewState.Loaded -> {
                    searchPlayersListView.state = LOAD_WITH_DATA
                }
                is PlayerViewState.Players -> {
                    searchPlayersListView.setData(it.players)
                }
                is PlayerViewState.Empty -> {
                    searchPlayersListView.state = LOAD_EMPTY
                }
                is PlayerViewState.Error -> {
                    searchPlayersListView.state = ERROR
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
            R.id.search -> {
                searchPlayersViewModel.searchListener = {
                    //TODO: Grab user search term
                    ""
                }
                searchPlayersViewModel.invalidateData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}